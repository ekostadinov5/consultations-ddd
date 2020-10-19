import React, { useEffect, useState } from "react";
import Moment from "react-moment";
import { Button, Modal } from "react-bootstrap";
import moment from "moment";
import RoomService from "../../../../../repository/axiosRoomRepository";
import LocalStorageService from "../../../../../service/localStorageService";

const AdditionalConsultationSlot = (props) => {

    const [studentAdded, setStudentAdded] = useState(false);
    const [roomName, setRoomName] = useState("");
    const [show, setShow] = useState(false);
    const [showDialogModal, setShowDialogModal] = useState(false);
    
    useEffect(() => {
        let studentAdded = false;
        for (let cs of props.studentConsultationSlots) {
            if (cs.consultationSlotId && props.value.id && cs.consultationSlotId.id === props.value.id.id) {
                studentAdded = true;
                break;
            }
        }
        setStudentAdded(studentAdded);
        RoomService.fetchById(props.value.roomId.id).then((promise) => {
            setRoomName(promise.data.name);
        });
    }, [props.studentConsultationSlots, props.value]);

    const addStudentToConsultationSlot = () => {
        const subjectId = document.getElementById("subject").value;
        const note = document.getElementById("note").value;

        props.onStudentAddedToConsultationSlotSlot(props.professorId, props.value.id.id,
            moment(props.value.date).format("DD.MM.YYYY"), props.value.from, props.value.roomId.id, subjectId,
            note);

        handleClose();
    }

    const removeStudentFromConsultationSlot = () => {
        props.onStudentRemovedFromConsultationSlotSlot(props.professorId, props.value.id.id);

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
                    {/*<div className="form-group">*/}
                    {/*    <label htmlFor="date" className="font-weight-bold">Датум:</label>*/}
                    {/*    <input id="date" className="form-control"*/}
                    {/*           value={moment(props.value.date).format("DD.MM.YYYY")} disabled={true} />*/}
                    {/*</div>*/}
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
                </div>

                { cardButton() }
            </div>

            { formModal() }
            { dialogModal() }
        </div>
    );
}

export default AdditionalConsultationSlot;
