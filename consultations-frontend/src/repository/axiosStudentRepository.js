import axios from "../custom-axios/axios";
import qs from "qs";

const StudentService = {
    fetchById: (studentId) => {
        return axios.get(`http://localhost:8082/api/students/${studentId}`);
    },
    fetchByIndex: (studentIndex) => {
        return axios.get(`http://localhost:8082/api/students/index/${studentIndex}`);
    },
    followProfessor: (studentId, professorId) => {
        const formParams = qs.stringify({
            studentId: studentId,
            professorId: professorId,
        });
        return axios.post("http://localhost:8082/api/students/follow", formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    },
    unfollowProfessor: (studentId, professorId) => {
        const formParams = qs.stringify({
            studentId: studentId,
            professorId: professorId,
        });
        return axios.post("http://localhost:8082/api/students/unfollow", formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    },
    addToConsultationSlot: (studentId, professorId, consultationSlotId, date, from, roomId, subjectId, note) => {
        const formParams = qs.stringify({
            studentId: studentId,
            professorId: professorId,
            consultationSlotId: consultationSlotId,
            date: date,
            from: from,
            roomId: roomId,
            subjectId: subjectId,
            note: note
        });
        return axios.post("http://localhost:8082/api/students/add", formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    },
    removeFromConsultationSlot: (studentId, professorId, consultationSlotId) => {
        const formParams = qs.stringify({
            studentId: studentId,
            professorId: professorId,
            consultationSlotId: consultationSlotId,
        });
        return axios.post("http://localhost:8082/api/students/remove", formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    }
}

export default StudentService;
