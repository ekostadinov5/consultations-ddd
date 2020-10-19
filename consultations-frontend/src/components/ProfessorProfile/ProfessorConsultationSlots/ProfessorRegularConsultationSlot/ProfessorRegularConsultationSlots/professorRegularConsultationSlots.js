import React, { useEffect, useState } from "react";
import moment from "moment";
import Moment from "react-moment";
import { Link } from "react-router-dom";
import { Button, Modal } from "react-bootstrap";
import TimeField from "react-simple-timefield";

const ProfessorRegularConsultationSlots = (props) => {

    const [selectedConsultationSlot, setSelectedConsultationSlot] = useState({ students: [] });
    const [show, setShow] = useState(false);
    const [showDialogModalDelete, setShowDialogModalDelete] = useState(false);
    const [showDialogModalCancel, setShowDialogModalCancel] = useState(false);
    const [dayOfWeek, setDayOfWeek] = useState(props.value.dayOfWeek);
    const [from, setFrom] = useState(props.value.from);
    const [to, setTo] = useState(props.value.to);
    const [building, setBuilding] = useState();
    const [roomId, setRoomId] = useState();
    const [roomName, setRoomName] = useState();

    useEffect(() => {
        setSelectedConsultationSlot(props.sortedConsultationSlots[props.selectedConsultationSlotIndex]);
        let room = undefined;
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
    }, [props.value, props.sortedConsultationSlots, props.selectedConsultationSlotIndex, props.roomsByBuilding]);

    const convertDay = (day) => {
        if(day === 'MONDAY') {
            return 'Понеделник';
        } else if(day === 'TUESDAY') {
            return 'Вторник';
        } else if(day === 'WEDNESDAY') {
            return 'Среда';
        } else if(day === 'THURSDAY') {
            return 'Четврток';
        } else if(day === 'FRIDAY') {
            return 'Петок';
        } else if(day === 'SATURDAY') {
            return 'Сабота';
        } else if(day === 'SUNDAY') {
            return 'Недела';
        } else {
            return null;
        }
    }

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

    const updateRegularConsultationSlot = () => {
        props.onRegularConsultationSlotUpdated(props.value.id.id, roomId, dayOfWeek, from, to);

        handleClose();
    }

    const deleteRegularConsultationSlot = () => {
        props.onRegularConsultationSlotDeleted(props.value.id.id);

        handleCloseDialogModalDelete();
        handleClose();
    }

    const cancelConsultationSlot = () => {
        props.onConsultationSlotCanceled(props.value.id.id, selectedConsultationSlot.id.id);

        handleCloseDialogModalCancel();
    }

    const uncancelConsultationSlot = () => {
        props.onConsultationSlotUncanceled(props.value.id.id, selectedConsultationSlot.id.id);
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
                        <label htmlFor="dayOfWeek" className="font-weight-bold">Ден:</label>
                        <select id="dayOfWeek" className="form-control" value={dayOfWeek}
                                onChange={(e) => setDayOfWeek(e.target.value)}>
                            <option value={"MONDAY"}>Понеделник</option>
                            <option value={"TUESDAY"}>Вторник</option>
                            <option value={"WEDNESDAY"}>Среда</option>
                            <option value={"THURSDAY"}>Четврток</option>
                            <option value={"FRIDAY"}>Петок</option>
                            <option value={"SATURDAY"}>Сабота</option>
                            <option value={"SUNDAY"}>Недела</option>
                        </select>
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
                    <Button variant="primary" onClick={updateRegularConsultationSlot}>
                        Измени
                    </Button>
                    <Button variant="danger" onClick={handleShowDialogModalDelete}>
                        Отстрани
                    </Button>
                    <Button variant="secondary" onClick={handleClose}>
                        Назад
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }



    const onConsultationSlotChange = (e) => {
        const selectedConsultationSlotId = e.target.value;
        props.setSelectedConsultationSlotIndex(props.sortedConsultationSlots
            .findIndex(cs => cs.id.id === selectedConsultationSlotId))
    }

    const cancelUncancelButton = () => {
        if (selectedConsultationSlot.canceled) {
            return (
                <button className="btn btn-success btn-sm btn-block" title="Врати во активни"
                        onClick={uncancelConsultationSlot}>
                    <i className="fa fa-plus" />
                </button>
            );
        } else {
            return (
                <button className="btn btn-danger btn-sm btn-block" title="Откажи"
                        onClick={handleShowDialogModalCancel}>
                    <i className="fa fa-minus" />
                </button>
            );
        }
    }

    const handleCloseDialogModalDelete = () => setShowDialogModalDelete(false);
    const handleShowDialogModalDelete = () => {
        setShowDialogModalDelete(true)
        setShow(false);
    }

    const dialogModalDelete = () => {
        return (
            <Modal show={showDialogModalDelete} onHide={handleCloseDialogModalDelete} animation={false}>
                <Modal.Header closeButton />
                <Modal.Body>
                    Дали сте сигурни дека сакате да го избришете редовниот консултациски термин?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={deleteRegularConsultationSlot}>
                        Да
                    </Button>
                    <Button variant="secondary" onClick={handleCloseDialogModalDelete}>
                        Не
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

    const handleCloseDialogModalCancel = () => setShowDialogModalCancel(false);
    const handleShowDialogModalCancel = () => setShowDialogModalCancel(true);

    const dialogModalCancel = () => {
        return (
            <Modal show={showDialogModalCancel} onHide={handleCloseDialogModalCancel} animation={false}>
                <Modal.Header closeButton />
                <Modal.Body>
                    Дали сте сигурни дека сакате да го откажете консултацискиот термин на датум {moment(selectedConsultationSlot.date).format("DD.MM.YYYY")}?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={cancelConsultationSlot}>
                        Да
                    </Button>
                    <Button variant="secondary" onClick={handleCloseDialogModalCancel}>
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
        <div className="card">
            <div className="card-body small">
                <div className="row mb-2">
                    <div className="col-6 text-right font-weight-bold">
                        Ден:
                    </div>

                    <div className="col-6">
                        {convertDay(props.value.dayOfWeek)}
                    </div>
                </div>

                <div className="row mb-2">
                    <div className="col-6 text-right font-weight-bold">
                        Датум:
                    </div>

                    <div className="col-6">
                        <select id="date" onChange={onConsultationSlotChange}>
                            {
                                props.sortedConsultationSlots.map(cs =>
                                    <option key={cs.id.id} value={cs.id.id}>
                                        { moment(cs.date).format("DD.MM.YYYY") }
                                    </option>
                                )
                            }
                        </select>
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
                        {selectedConsultationSlot.students.length}
                    </div>
                </div>
            </div>

            <div className="card-footer">
                <div className="row">
                    <div className="col px-2">
                        <Link to={{
                            pathname: "/consultationSlotStudents",
                            state: {
                                students: selectedConsultationSlot.students
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
                    <div className="col px-2">
                        {selectedConsultationSlot ? cancelUncancelButton() : null}
                    </div>
                </div>
            </div>

            { dialogModalDelete() }
            { dialogModalCancel() }

        </div>
    );
}

export default ProfessorRegularConsultationSlots;
