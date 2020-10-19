import React, { useEffect, useState } from "react";
import {Button, Modal} from "react-bootstrap";
import Moment from "react-moment";
import ProfessorService from "../../../repository/axiosProfessorRepository";
import RoomService from "../../../repository/axiosRoomRepository";

const StudentConsultationSlot = (props) => {

    const [scsi, setScsi] = useState(null); // studentConsultationSlotInfo
    const [room, setRoom] = useState({})
    const [show, setShow] = useState(false);
    const [showDialogModal, setShowDialogModal] = useState(false);

    useEffect(() => {
        ProfessorService.getStudentConsultationSlotInfo(props.value.professorId.id, props.value.consultationSlotId.id,
            props.value.subjectId.id).then((promise) => {
                setScsi(promise.data);
        });
        RoomService.fetchById(props.value.roomId.id).then((promise) => {
            setRoom(promise.data);
        });
    }, [props.value]);

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

    const removeStudentFromConsultationSlot = () => {
        props.onStudentRemovedFromConsultationSlotSlot(props.value.professorId.id, props.value.consultationSlotId.id);

        handleCloseDialogModal();
    }

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const readMoreOfNote = () => {
        return props.value.note.length > 50 ?
            (
                <>
                    ...
                    <button className="btn btn-sm btn-link p-0 ml-lg-2" onClick={handleShow}>
                        <small>Повеќе</small>
                    </button>
                    <Modal show={show} onHide={handleClose} animation={false}>
                        <Modal.Header closeButton>
                            <h5 className="mb-0">Забелешка</h5>
                        </Modal.Header>
                        <Modal.Body>
                            <p className="text-break">
                                {props.value.note}
                            </p>
                        </Modal.Body>
                    </Modal>
                </>
            )
            :
            null;
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

    return scsi ? (
        <tr>
            <td className="align-middle">{convertTitle(scsi.professorTitle)} {scsi.professorFullName.firstName} {scsi.professorFullName.lastName}</td>
            <td className="align-middle">
                <Moment parse="YYYY-MM-DD" format="DD.MM.YYYY">
                    {scsi.date}
                </Moment>
            </td>
            <td className="align-middle">
                <Moment parse="hh:mm:ss" format="HH:mm">
                    {scsi.from}
                </Moment>
                -
                <Moment parse="hh:mm:ss" format="HH:mm">
                    {scsi.to}
                </Moment>
            </td>
            <td className="align-middle">{room.name}</td>
            <td className="align-middle">{scsi.subjectName}</td>
            <td className="align-middle">
                {props.value.note.length > 0 ? props.value.note.substring(0, 50) : "-"}
                {readMoreOfNote()}
            </td>
            <td className="align-middle">
                <button className="btn btn-danger btn-sm" title="Откажи" onClick={handleShowDialogModal}>
                    <i className="fa fa-xs fa-times" />
                </button>

                { dialogModal() }
            </td>
        </tr>
    ) : null;
}

export default StudentConsultationSlot;
