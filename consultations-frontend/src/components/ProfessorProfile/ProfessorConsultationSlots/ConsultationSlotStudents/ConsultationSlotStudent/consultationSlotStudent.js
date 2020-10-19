import React, {useState} from "react";
import { Modal } from "react-bootstrap";

const ConsultationSlotStudent = (props) => {

    const [show, setShow] = useState(false);

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

    return (
        <tr>
            <td className="align-middle">{props.value.studentIndex.index}</td>
            <td className="align-middle">{props.value.studentFullName.firstName}</td>
            <td className="align-middle">{props.value.studentFullName.lastName}</td>
            <td className="align-middle">{props.subjectName}</td>
            <td className="align-middle">
                {props.value.note.length > 0 ? props.value.note.substring(0, 50) : "-"}
                {readMoreOfNote()}
            </td>
        </tr>
    );
}

export default ConsultationSlotStudent;
