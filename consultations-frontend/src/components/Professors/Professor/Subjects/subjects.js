import React from "react";

const Subjects = (props) => {

    const subjects = () => {
        return props.subjects.map(subject => <li key={subject.name}>{subject.name}</li>);
    }

    return (
        <ul>
            {subjects()}
        </ul>
    );
}

export default Subjects;
