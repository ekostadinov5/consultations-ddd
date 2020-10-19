import React, { useState } from "react";
import ProfessorConsultationSlots from "./ProfessorConsultationSlots/professorConsultationSlots";
import { Button, Modal } from "react-bootstrap";
import DatePicker from "react-datepicker";
import TimeField from 'react-simple-timefield';

const ProfessorProfile = (props) => {

    const [show, setShow] = useState(false);
    const [regularOrAdditional, setRegularOrAdditional] = useState("regular");
    const [date, setDate] = useState(new Date());
    const [building, setBuilding] = useState(Object.keys(props.roomsByBuilding)[0]);

    const convertTitle = (title) => {
        if (title === "A") {
            return "Академик д-р"
        } else if (title === "D") {
            return "д-р"
        } else if (title === "M") {
            return "м-р"
        } else {
            return "спец."
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

    const createConsultationSlot = () => {
        const roomId = document.getElementById("room").value;
        const from = document.getElementById("from").value;
        const to = document.getElementById("to").value;
        if (regularOrAdditional === "regular") {
            const dayOfWeek = document.getElementById("dayOfWeek").value;
            props.onRegularConsultationSlotCreated(roomId, dayOfWeek, from, to);
        } else {
            const date = document.getElementById("consultationSlotDate").value;
            props.onAdditionalConsultationSlotCreated(roomId, date, from, to);
        }

        handleClose();
    }

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const dayOrDate = () => {
        if (regularOrAdditional === "regular") {
            return (
                <div className="form-group">
                    <label htmlFor="dayOfWeek" className="font-weight-bold">Ден:</label>
                    <select id="dayOfWeek" className="form-control">
                        <option value={"MONDAY"}>Понеделник</option>
                        <option value={"TUESDAY"}>Вторник</option>
                        <option value={"WEDNESDAY"}>Среда</option>
                        <option value={"THURSDAY"}>Четврток</option>
                        <option value={"FRIDAY"}>Петок</option>
                        <option value={"SATURDAY"}>Сабота</option>
                        <option value={"SUNDAY"}>Недела</option>
                    </select>
                </div>
            );
        } else {
            return (
                <div className="form-group">
                    <label htmlFor="consultationSlotDate" className="font-weight-bold d-block">Датум:</label>
                    <DatePicker id="consultationSlotDate"
                                className="form-control"
                                selected={date}
                                onChange={(date) => setDate(date)}
                                dateFormat="dd.MM.yyyy"
                                minDate={date} />
                </div>
            );
        }
    }

    const createConsultationSlotModal = () => {
        return (
            <Modal show={show} onHide={handleClose} animation={false}>
                <Modal.Header closeButton>
                    <h5 className="mb-0">Додади консултациски термин</h5>
                </Modal.Header>
                <Modal.Body>
                    <div className="form-group">
                        <label htmlFor="type" className="font-weight-bold">Тип:</label>
                        <select id="type" className="form-control" value={regularOrAdditional}
                                onChange={(e) => setRegularOrAdditional(e.target.value)}>
                            <option value="regular">Редовен термин</option>
                            <option value="additional">Дополнителен термин</option>
                        </select>
                    </div>

                    {dayOrDate()}

                    <div className="form-group">
                        <label htmlFor="from" className="font-weight-bold">Почеток:</label>
                        <TimeField
                            id={"from"}
                            className={"form-control"}
                            input={<input type={"text"}/>}
                            value="12:00"/>
                    </div>

                    <div className="form-group">
                        <label htmlFor="to" className="font-weight-bold">Крај:</label>
                        <TimeField
                            id={"to"}
                            className={"form-control"}
                            input={<input type={"text"}/>}
                            value="13:00"/>
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
                        <select id="room" className="form-control">
                            {getRoomsByBuilding()}
                        </select>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="success" onClick={createConsultationSlot}>
                        Додади
                    </Button>
                    <Button variant="secondary" onClick={handleClose}>
                        Назад
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

    return props.professor.fullName ? (
        <div className="row">
            <div className="col-10 m-auto">
                <div className="row">
                    <div className="col-12 col-sm-8 text-center text-sm-left mb-3 mb-sm-0">
                        <span className="lead">
                            {convertTitle(props.professor.title)} {props.professor.fullName.firstName} {props.professor.fullName.lastName}
                        </span>
                    </div>

                    <div className="col-12 col-sm-4 text-center text-sm-right">
                        <button className="btn btn-success btn-sm" onClick={handleShow}>
                            Додади термин
                        </button>
                        {createConsultationSlotModal()}
                    </div>
                </div>

                <hr/>

                <div className="row">
                    <ProfessorConsultationSlots regularConsultationSlots={props.regularConsultationSlots}
                                                additionalConsultationSlots={props.additionalConsultationSlots}
                                                roomsByBuilding={props.roomsByBuilding}
                                                onRegularConsultationSlotUpdated=
                                                    {props.onRegularConsultationSlotUpdated}
                                                onRegularConsultationSlotDeleted=
                                                    {props.onRegularConsultationSlotDeleted}
                                                onConsultationSlotCanceled={props.onConsultationSlotCanceled}
                                                onConsultationSlotUncanceled={props.onConsultationSlotUncanceled}
                                                onAdditionalConsultationSlotUpdated=
                                                    {props.onAdditionalConsultationSlotUpdated}
                                                onAdditionalConsultationSlotDeleted=
                                                    {props.onAdditionalConsultationSlotDeleted}
                    />
                </div>
            </div>
        </div>
    ) : null;
}

export default ProfessorProfile;
