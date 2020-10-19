import React, { useEffect, useState } from "react";
import Moment from "react-moment";
import { Button, Modal } from "react-bootstrap";
import moment from "moment";
import RoomService from "../../../../../repository/axiosRoomRepository";
import LocalStorageService from "../../../../../service/localStorageService";

const RegularConsultationSlot = (props) => {

    const [studentAdded, setStudentAdded] = useState(false);
    const [chosenConsultationSlot, setChosenConsultationSlot] = useState(null);
    const [roomName, setRoomName] = useState("");
    const [show, setShow] = useState(false);
    const [showDialogModal, setShowDialogModal] = useState(false);
    
    useEffect(() => {
        let studentAdded = false;
        for (let cs1 of props.studentConsultationSlots) {
            for (let cs2 of props.value.consultationSlots) {
                if (cs1.consultationSlotId && cs2.id && cs1.consultationSlotId.id === cs2.id.id) {
                    studentAdded = true;
                    setChosenConsultationSlot(cs2);
                }
            }
        }
        setStudentAdded(studentAdded);
        RoomService.fetchById(props.value.roomId.id).then((promise) => {
            setRoomName(promise.data.name);
        });
    }, [props.studentConsultationSlots, props.value]);

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

    const getSortedUncancelledConsultationSlots = () => {
        return props.value.consultationSlots.filter(cs => !cs.canceled)
            .sort((cs1, cs2) => new Date(cs1.date) - new Date(cs2.date));
    }

    const addStudentToConsultationSlot = () => {
        const subjectId = document.getElementById("subject").value;
        const consultationSlot = document.getElementById("date");
        const consultationSlotId = consultationSlot.value;
        const consultationSlotDate = consultationSlot.options[consultationSlot.selectedIndex].text;
        const note = document.getElementById("note").value;

        props.onStudentAddedToConsultationSlotSlot(props.professorId, consultationSlotId, consultationSlotDate,
            props.value.from, props.value.roomId.id, subjectId, note);

        handleClose();
    }

    const removeStudentFromConsultationSlot = () => {
        props.onStudentRemovedFromConsultationSlotSlot(props.professorId, chosenConsultationSlot.id.id);

        handleCloseDialogModal();
    }

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const formModal = () => {
        return (
            <Modal show={show} onHide={handleClose} animation={false}>
                <Modal.Header closeButton />
                <Modal.Body>
                    <div className="form-group">
                        <label htmlFor="subject" className="font-weight-bold">Предмет:</label>
                        <select className="form-control" id="subject">
                            {props.subjects.map(s => <option key={s.id.id} value={s.id.id}>{s.name}</option>)}
                            <option value={"0"}>Останато</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label htmlFor="date" className="font-weight-bold">Датум:</label>
                        <select id="date" className="form-control">
                            {
                                getSortedUncancelledConsultationSlots().map(cs =>
                                    <option key={cs.id.id} value={cs.id.id}>
                                        { moment(cs.date).format("DD.MM.YYYY") }
                                    </option>
                                )
                            }
                        </select>
                    </div>
                    <div className="form-group">
                        <label htmlFor={"note"} className="font-weight-bold">Забелешка:</label>
                        <textarea className="form-control" rows={7} id="note"/>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="success" onClick={addStudentToConsultationSlot}>
                        Потврди
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

    const handleCloseDialogModal = () => setShowDialogModal(false);
    const handleShowDialogModal = () => setShowDialogModal(true);

    const dialogModal = () => {
        return (
            <Modal show={showDialogModal} onHide={handleCloseDialogModal} animation={false}>
                <Modal.Header closeButton />
                <Modal.Body>
                    Дали сте сигурни дека сакате да го откажете вашето присуство на консултацискиот термин?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={removeStudentFromConsultationSlot}>
                        Да
                    </Button>
                    <Button variant="secondary" onClick={handleCloseDialogModal}>
                        Не
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

    const cardButton = () => {
        if (LocalStorageService.getRole() === "STUDENT") {
            if (studentAdded) {
                return (
                    <div className="card-footer p-0">
                        <button onClick={handleShowDialogModal} className="btn btn-danger btn-block">
                            Откажи
                        </button>
                    </div>
                );
            } else {
                return (
                    <div className="card-footer p-0">
                        <button onClick={handleShow} className="btn btn-success btn-block">
                            Пријави се
                        </button>
                    </div>
                );
            }
        }
    }

    return (
        <div className="col-12 col-md-6 mb-3">
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
                </div>

                { cardButton() }
            </div>

            { formModal() }
            { dialogModal() }
        </div>
    );
}

export default RegularConsultationSlot;
