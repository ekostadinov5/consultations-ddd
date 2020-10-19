import React from "react";
import Building from "./Building/building";

const Rooms = (props) => {

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

    const buildings = () => {
        const dict = [];
        const keys = Object.keys(props.roomsByBuilding);
        keys.forEach(k => dict.push([k, convertBuildingName(k)]));
        dict.sort((b1, b2) => b1[1].localeCompare(b2[1]));
        return dict.map(b => <Building key={b[0]} buildingName={b[1]} rooms={props.roomsByBuilding[b[0]]} />);
    }

    return (
        <>
            <p className="lead d-inline-block m-auto m-sm-0">
                Простории
            </p>

            <hr />

            <div className="row">
                {buildings()}
            </div>
        </>
    );
}

export default Rooms;
