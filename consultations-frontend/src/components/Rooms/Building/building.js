import React from "react";
import Room from "./Room/room";

const Building = (props) => {

    const rooms = () => {
        return props.rooms.map(r => <Room key={r.id.id} value={r} />)
    }

    return (
        <div className="col-12 text-center mb-5">
            <h6>{props.buildingName}</h6>

            <hr className="w-25" />

            <div className="row">
                {rooms()}
            </div>
        </div>
    );
}

export default Building;
