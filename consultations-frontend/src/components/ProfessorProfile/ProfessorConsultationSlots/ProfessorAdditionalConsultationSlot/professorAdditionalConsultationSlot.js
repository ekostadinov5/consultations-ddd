import React, {useEffect, useState} from "react";
import Moment from "react-moment";
import {Link} from "react-router-dom";
import {Button, Modal} from "react-bootstrap";
import TimeField from "react-simple-timefield";
import DatePicker from "react-datepicker";

const ProfessorAdditionalConsultationSlot = (props) => {

    const [show, setShow] = useState(false);
    const [showDialogModal, setShowDialogModal] = useState(false);
    const [date, setDate] = useState(new Date(props.value.date));
    const [from, setFrom] = useState(props.value.from);
    const [to, setTo] = useState(props.value.to);
    const [building, setBuilding] = useState();
    const [roomId, setRoomId] = useState();
    const [roomName, setRoomName] = useState();

    useEffect(() => {
        let room = null;
        Object.keys(props.roomsByBuilding).forEach(k => {
            props.roomsByBuilding[k].forEach(r => {
                if (r.id.id === props.value.roomId.id) {
                    room = r;
                }
            })
        });
        setBuilding(room.building);
        setRoomId(room.id.id);
        setRoomName(room.name);
    }, [props.value, props.roomsByBuilding]);

    const convertBuildingName = (building) => {
        if (building === "AF") {
            return "Анекс на ФЕИТ";
        } else if (building === "B") {
            return "Бараки";
        } else if (building === "D") {
            return "Деканат на ФИНКИ"
        } else if (building === "F") {
            return "ФИНКИ";
        } else if (building === "MF") {
            return "Машински факултет / ФЕИТ";
        } else {
            return "Технолошко-металуршки факултет";
        }
    }

    const updateAdditionalConsultationSlot = () => {
        const date = document.getElementById("consultationSlotDate").value;
        props.onAdditionalConsultationSlotUpdated(props.value.id.id, roomId, date, from, to);

        handleClose();
    }

    const deleteAdditionalConsultationSlot = () => {
        props.onAdditionalConsultationSlotDeleted(props.value.id.id);

        handleCloseDialogModal();
    }

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const updateDeleteConsultationSlotModal = () => {
        return (
            <Modal show={show} onHide={handleClose} animation={false}>
                <Modal.Header closeButton>
                    <h5 className="mb-0">Измени консултациски термин</h5>
                </Modal.Header>
                <Modal.Body>
                    <div className="form-group">
                        <label htmlFor="consultationSlotDate" className="font-weight-bold d-block">Датум:</label>
                        <DatePicker id="consultationSlotDate"
                                    className="form-control"
                                    selected={date}
                                    onChange={(date) => setDate(date)}
                                    dateFormat="dd.MM.yyyy"
                                    minDate={new Date()} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="from" className="font-weight-bold">Почеток:</label>
                        <TimeField
                            id={"from"}
                            className={"form-control"}
                            input={<input type={"text"} />}
                            value={from}
                            onChange={(e) => setFrom(e.target.value)} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="to" className="font-weight-bold">Крај:</label>
                        <TimeField
                            id={"to"}
                            className={"form-control"}
                            input={<input type={"text"} />}
                            value={to}
                            onChange={(e) => setTo(e.target.value)} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="building" className="font-weight-bold">Зграда:</label>
                        <select id="building" className="form-control" value={building}
                                onChange={(e) => setBuilding(e.target.value)}>
                            {getBuildings()}
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="room" className="font-weight-bold">Просторија:</label>
                        <select id="room" className="form-control" value={roomId}
                                onChange={(e) => setRoomId(e.target.value)}>
                            {getRoomsByBuilding()}
                        </select>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={updateAdditionalConsultationSlot}>
                        Измени
                    </Button>
                    <Button variant="danger" onClick={handleShowDialogModal}>
                        Отстрани
                    </Button>
                    <Button variant="secondary" onClick={handleClose}>
                        Назад
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

    const handleCloseDialogModal = () => setShowDialogModal(false);
    const handleShowDialogModal = () => {
        setShowDialogModal(true);
        setShow(false);
    }

    const dialogModal = () => {
        return (
            <Modal show={showDialogModal} onHide={handleCloseDialogModal} animation={false}>
                <Modal.Header closeButton />
                <Modal.Body>
                    Дали сте сигурни дека сакате да го избришете дополнителниот консултациски термин?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={deleteAdditionalConsultationSlot}>
                        Да
                    </Button>
                    <Button variant="secondary" onClick={handleCloseDialogModal}>
                        Не
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

    const getBuildings = () => {
        const dict = [];
        const keys = Object.keys(props.roomsByBuilding);
        keys.forEach(k => dict.push([k, convertBuildingName(k)]));
        dict.sort((b1, b2) => b1[1].localeCompare(b2[1]));
        return dict.map(b => <option key={b[0]} value={b[0]}>{convertBuildingName(b[0])}</option>);
    }

    const getRoomsByBuilding = () => {
        return props.roomsByBuilding[building] ?
            props.roomsByBuilding[building].map(r => <option key={r.id.id} value={r.id.id}>{r.name}</option>) : null;
    }

    return (
        <div className="col-12 col-md-6 col-lg-4 mb-3">
            <div className="card">
                <div className="card-body small">
                    <div className="row mb-2">
                        <div className="col-6 text-right font-weight-bold">
                            Датум:
                        </div>

                        <div className="col-6">
                            <Moment parse={"YYYY-MM-DD"} format={"DD.MM.YYYY"}>
                                {props.value.date}
                            </Moment>
                        </div>
                    </div>

                    <div className="row mb-2">
                        <div className="col-6 text-right font-weight-bold">
                            Време:
                        </div>

                        <div className="col-6">
                            <Moment parse="hh:mm:ss" format="HH:mm">
                                {props.value.from}
                            </Moment>

                            -

                            <Moment parse="hh:mm:ss" format="HH:mm">
                                {props.value.to}
                            </Moment>
                        </div>
                    </div>

                    <div className="row mb-2">
                        <div className="col-6 text-right font-weight-bold">
                            Просторија:
                        </div>

                        <div className="col-6">
                            {roomName}
                        </div>
                    </div>

                    <div className="row mb-2">
                        <div className="col-6 text-right font-weight-bold">
                            Студенти:
                        </div>

                        <div className="col-6">
                            {props.value.students.length}
                        </div>
                    </div>
                </div>

                <div className="card-footer">
                    <div className="row">
                        <div className="col px-2">
                            <Link to={{
                                pathname: "/consultationSlotStudents",
                                state: {
                                    students: props.value.students
                                }
                            }}
                                  className="btn btn-info btn-sm btn-block" title="Пријавени студенти">
                                <i className="fa fa-user-alt" />
                            </Link>
                        </div>
                        <div className="col px-2">
                            <button className="btn btn-primary btn-sm btn-block" title="Измени / отстрани"
                                    onClick={handleShow}>
                                <i className="fa fa-edit" />
                            </button>
                            {updateDeleteConsultationSlotModal()}
                        </div>
                    </div>
                </div>
            </div>

            { dialogModal() }

        </div>
    );
}

export default ProfessorAdditionalConsultationSlot;
