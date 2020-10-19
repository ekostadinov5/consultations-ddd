import axios from "../custom-axios/axios";
import qs from "qs";

const ProfessorService = {
    fetchProfessors: (page, pageSize) => {
        return axios.get("http://localhost:8080/api/professors", {
            headers: {
                page: page,
                pageSize: pageSize
            }
        });
    },
    searchProfessors: (searchTerm) => {
        return axios.get(`http://localhost:8080/api/professors?term=${searchTerm}`);
    },
    fetchFollowedProfessors: (followedProfessorsIds) => {
        return axios.get("http://localhost:8080/api/professors/listByIds", {
            headers: {
                professorsIds: followedProfessorsIds
            }
        });
    },
    fetchById: (professorId) => {
        return axios.get(`http://localhost:8080/api/professors/${professorId}`);
    },
    fetchByUsername: (professorUsername) => {
        return axios.get(`http://localhost:8080/api/professors/byUsername/${professorUsername}`);
    },
    getStudentConsultationSlotInfo: (professorId, consultationSlotId, subjectId) => {
        return axios.get(`http://localhost:8080/api/professors/studentConsultationSlotInfo/${professorId}`, {
            headers: {
                consultationSlotId: consultationSlotId,
                subjectId: subjectId
            }
        });
    },
    createRegularConsultationSlot: (professorId, roomId, dayOfWeek, from, to) => {
        const formParams = qs.stringify({
            professorId: professorId,
            roomId: roomId,
            dayOfWeek: dayOfWeek,
            from: from,
            to: to
        });
        return axios.post("http://localhost:8080/api/professors/regularConsultationSlots", formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    },
    updateRegularConsultationSlot: (professorId, regularConsultationSlotId, roomId, dayOfWeek, from, to) => {
        const formParams = qs.stringify({
            regularConsultationSlotId: regularConsultationSlotId,
            roomId: roomId,
            dayOfWeek: dayOfWeek,
            from: from,
            to: to
        });
        return axios
            .patch(`http://localhost:8080/api/professors/regularConsultationSlots/${professorId}`,
                formParams,
                {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                });
    },
    deleteRegularConsultationSlot: (professorId, regularConsultationSlotId) => {
        return axios
            .delete(`http://localhost:8080/api/professors/regularConsultationSlots/${professorId}/${regularConsultationSlotId}`);
    },
    cancelConsultationSlot: (professorId, regularConsultationSlotId, consultationSlotId) => {
        const formParams = qs.stringify({
            regularConsultationSlotId: regularConsultationSlotId,
            consultationSlotId: consultationSlotId
        });
        return axios
            .patch(`http://localhost:8080/api/professors/regularConsultationSlots/cancel/${professorId}`,
                formParams,
                {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                });
    },
    uncancelConsultationSlot: (professorId, regularConsultationSlotId, consultationSlotId) => {
        const formParams = qs.stringify({
            regularConsultationSlotId: regularConsultationSlotId,
            consultationSlotId: consultationSlotId
        });
        return axios
            .patch(`http://localhost:8080/api/professors/regularConsultationSlots/uncancel/${professorId}`,
                formParams,
                {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                });
    },
    createAdditionalConsultationSlot: (professorId, roomId, date, from, to) => {
        const formParams = qs.stringify({
            professorId: professorId,
            roomId: roomId,
            date: date,
            from: from,
            to: to
        });
        return axios.post("http://localhost:8080/api/professors/additionalConsultationSlots", formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    },
    updateAdditionalConsultationSlot: (professorId, additionalConsultationSlotId, roomId, date, from, to) => {
        const formParams = qs.stringify({
            additionalConsultationSlotId: additionalConsultationSlotId,
            roomId: roomId,
            date: date,
            from: from,
            to: to
        });
        return axios
            .patch(`http://localhost:8080/api/professors/additionalConsultationSlots/${professorId}`,
                formParams,
                {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                });
    },
    deleteAdditionalConsultationSlot: (professorId, additionalConsultationSlotId) => {
        return axios
            .delete(`http://localhost:8080/api/professors/additionalConsultationSlots/${professorId}/${additionalConsultationSlotId}`);
    }
}

export default ProfessorService;
