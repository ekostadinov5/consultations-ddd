import axios from "../custom-axios/axios";

const RoomService = {
    fetchRooms: () => {
        return axios.get("http://localhost:8081/api/rooms");
    },
    fetchById: (roomId) => {
        return axios.get(`http://localhost:8081/api/rooms/${roomId}`);
    }
}

export default RoomService;
