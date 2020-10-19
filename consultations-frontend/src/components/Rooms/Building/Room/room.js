import React from "react";

const Room = (props) => {

    return (
        <div className="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
            <div className="card">
                <div className="card-header">
                    <p className="card-title">
                        {props.value.name}
                    </p>
                </div>
                <div className="card-body">
                    <p className="card-text">
                        {props.value.description}
                    </p>
                </div>
            </div>
        </div>
    );
}

export default Room;
