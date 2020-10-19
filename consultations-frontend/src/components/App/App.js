import React, {Component} from "react";
// import logo from "../../logo.svg";
import "./App.css";
import { BrowserRouter as Router, Redirect, Route } from 'react-router-dom';
import {Button, Modal} from "react-bootstrap";

import ProfessorService from "../../repository/axiosProfessorRepository";
import StudentService from "../../repository/axiosStudentRepository";
import RoomService from "../../repository/axiosRoomRepository";
import LocalStorageService from "../../service/localStorageService";

import Header from "../Header/header";
import Footer from "../Footer/footer";
import BackToTop from "../BackToTop/backToTop";

import Professors from "../Professors/professors";
import Professor from "../Professors/Professor/professor";

import ProfessorProfile from "../ProfessorProfile/professorProfile";
import ConsultationSlotStudents
    from "../ProfessorProfile/ProfessorConsultationSlots/ConsultationSlotStudents/consultationSlotStudents";

import StudentProfile from "../StudentProfile/studentProfile";

import Rooms from "../Rooms/rooms";

import Login from "../Login/login";

class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            professors: [],
            page: 0,
            pageSize: 0,
            totalPages: 0,
            followedProfessors: [],

            student: {},
            studentProfessorsFollowed: [],
            studentConsultationSlots: [],

            professor: {},
            regularConsultationSlots: [],
            additionalConsultationSlots: [],

            roomsByBuilding: [],

            showErrorModal: false,
            errorMessage: ""
        }
    }

    ProfessorsApi = {
        loadProfessors: (page = 0, pageSize = 24) => {
            ProfessorService.fetchProfessors(page, pageSize).then((promise) => {
                this.setState({
                    professors: promise.data.content,
                    page: promise.data.number,
                    pageSize: promise.data.size,
                    totalPages: promise.data.totalPages
                });
            });
        },
        searchProfessors: (searchTerm) => {
            if (searchTerm === "") {
                this.ProfessorsApi.loadProfessors(this.state.page);
            } else {
                ProfessorService.searchProfessors(searchTerm).then((promise) => {
                    this.setState({
                        professors: promise.data,
                        totalPages: 1
                    });
                });
            }
        },
        loadProfessor: (professorId) => {
            ProfessorService.fetchById(professorId).then((promise) => {
                this.setState({
                    professor: promise.data,
                    regularConsultationSlots: promise.data.regularConsultationSlots,
                    additionalConsultationSlots: promise.data.additionalConsultationSlots,
                });
            });
        },
        loadProfessorByUsername: (professorUsername) => {
            ProfessorService.fetchByUsername(professorUsername).then((promise) => {
                this.setState({
                    professor: promise.data,
                    regularConsultationSlots: promise.data.regularConsultationSlots,
                    additionalConsultationSlots: promise.data.additionalConsultationSlots,
                });
            });
        },
        createRegularConsultationSlot: (roomId, dayOfWeek, from, to) => {
            ProfessorService.createRegularConsultationSlot(this.state.professor.id.id, roomId, dayOfWeek, from, to)
                .then((promise) => {
                    this.setState((prevState) => {
                        const newRegularConsultationSlotsRef = [...prevState.regularConsultationSlots, promise.data];
                        return {
                            regularConsultationSlots: newRegularConsultationSlotsRef
                        }
                    });
                }).catch((err) => {
                    if (err.response.data.exception === "mk.ukim.finki.emt.consultations.professormanagement.domain" +
                        ".model.exception.ConsultationSlotStartTimeAfterEndTimeException") {
                        const message = "Времето на почеток на консултацискиот термин треба да биде пред времето на " +
                            "неговиот крај!";
                        this.setState({errorMessage: message});
                        this.handleShowErrorModal();
                    }
            });
        },
        updateRegularConsultationSlot: (regularConsultationSlotId, roomId, dayOfWeek, from, to) => {
            ProfessorService
                .updateRegularConsultationSlot(this.state.professor.id.id, regularConsultationSlotId, roomId, dayOfWeek,
                    from ,to).then((promise) => {
                        this.setState((prevState) => {
                            const newRegularConsultationSlotsRef = prevState.regularConsultationSlots
                                .map(rcs => rcs.id.id !== regularConsultationSlotId ? rcs : promise.data);
                            return {
                                regularConsultationSlots: newRegularConsultationSlotsRef
                            }
                        });
            }).catch((err) => {
                if (err.response.data.exception === "mk.ukim.finki.emt.consultations.professormanagement.domain.model" +
                    ".exception.ConsultationSlotStartTimeAfterEndTimeException") {
                    const message = "Времето на почеток на консултацискиот термин треба да биде пред времето на " +
                        "неговиот крај!";
                    this.setState({errorMessage: message});
                    this.handleShowErrorModal();
                }
            });
        },
        deleteRegularConsultationSlot: (regularConsultationSlotId) => {
            ProfessorService.deleteRegularConsultationSlot(this.state.professor.id.id, regularConsultationSlotId)
                .then(() => {
                    this.setState((prevState) => {
                        const newRegularConsultationSlotsRef = prevState.regularConsultationSlots
                            .filter(rcs => rcs.id.id !== regularConsultationSlotId);
                        return {
                            regularConsultationSlots: newRegularConsultationSlotsRef
                        }
                    });
                });
        },
        cancelConsultationSlot: (regularConsultationSlotId, consultationSlotId) => {
            ProfessorService
                .cancelConsultationSlot(this.state.professor.id.id, regularConsultationSlotId, consultationSlotId)
                .then((promise) => {
                    this.setState((prevState) => {
                        const newRegularConsultationSlotsRef = prevState.regularConsultationSlots
                            .map(rcs => rcs.id.id !== regularConsultationSlotId ? rcs : promise.data);
                        return {
                            regularConsultationSlots: newRegularConsultationSlotsRef
                        }
                    });
                });
        },
        uncancelConsultationSlot: (regularConsultationSlotId, consultationSlotId) => {
            ProfessorService
                .uncancelConsultationSlot(this.state.professor.id.id, regularConsultationSlotId, consultationSlotId)
                .then((promise) => {
                    this.setState((prevState) => {
                        const newRegularConsultationSlotsRef = prevState.regularConsultationSlots
                            .map(rcs => rcs.id.id !== regularConsultationSlotId ? rcs : promise.data);
                        return {
                            regularConsultationSlots: newRegularConsultationSlotsRef
                        }
                    });
                });
        },
        createAdditionalConsultationSlot: (roomId, date, from, to) => {
            ProfessorService.createAdditionalConsultationSlot(this.state.professor.id.id, roomId, date, from, to)
                .then((promise) => {
                    this.setState((prevState) => {
                        const newAdditionalConsultationSlotsRef =
                            [...prevState.additionalConsultationSlots, promise.data];
                        return {
                            additionalConsultationSlots: newAdditionalConsultationSlotsRef
                        }
                    });
                }).catch((err) => {
                    if (err.response.data.exception === "mk.ukim.finki.emt.consultations.professormanagement.domain" +
                        ".model.exception.ConsultationSlotStartTimeInThePastException") {
                        const message = "Не може да закажете консултациски термин во минатото!";
                        this.setState({errorMessage: message});
                        this.handleShowErrorModal();
                    } else if (err.response.data.exception === "mk.ukim.finki.emt.consultations.professormanagement" +
                        ".domain.model.exception.ConsultationSlotStartTimeAfterEndTimeException") {
                        const message = "Времето на почеток на консултацискиот термин треба да биде пред времето на " +
                            "неговиот крај!";
                        this.setState({errorMessage: message});
                        this.handleShowErrorModal();
                    }
            });
        },
        updateAdditionalConsultationSlot: (additionalConsultationSlotId, roomId, date, from, to) => {
            ProfessorService
                .updateAdditionalConsultationSlot(this.state.professor.id.id, additionalConsultationSlotId, roomId,
                    date, from ,to).then((promise) => {
                this.setState((prevState) => {
                    const newAdditionalConsultationSlotsRef = prevState.additionalConsultationSlots
                        .map(acs => acs.id.id !== additionalConsultationSlotId ? acs : promise.data);
                    return {
                        additionalConsultationSlots: newAdditionalConsultationSlotsRef
                    }
                });
            }).catch((err) => {
                if (err.response.data.exception === "mk.ukim.finki.emt.consultations.professormanagement.domain.model" +
                    ".exception.ConsultationSlotStartTimeInThePastException") {
                    const message = "Не може да закажете консултациски термин во минатото!";
                    this.setState({errorMessage: message});
                    this.handleShowErrorModal();
                } else if (err.response.data.exception === "mk.ukim.finki.emt.consultations.professormanagement" +
                    ".domain.model.exception.ConsultationSlotStartTimeAfterEndTimeException") {
                    const message = "Времето на почеток на консултацискиот термин треба да биде пред времето на " +
                        "неговиот крај!";
                    this.setState({errorMessage: message});
                    this.handleShowErrorModal();
                }
            });
        },
        deleteAdditionalConsultationSlot: (additionalConsultationSlotId) => {
            ProfessorService.deleteAdditionalConsultationSlot(this.state.professor.id.id, additionalConsultationSlotId)
                .then(() => {
                    this.setState((prevState) => {
                        const newAdditionalConsultationSlotsRef = prevState.additionalConsultationSlots
                            .filter(acs => acs.id.id !== additionalConsultationSlotId);
                        return {
                            additionalConsultationSlots: newAdditionalConsultationSlotsRef
                        }
                    });
                });
        }
    }

    StudentsApi = {
        loadStudentByIndex: (studentIndex) => {
            StudentService.fetchByIndex(studentIndex).then((promise) => {
                this.setState({
                    student: promise.data,
                    studentProfessorsFollowed: promise.data.professorsFollowed,
                    studentConsultationSlots: promise.data.consultationSlots
                });
                ProfessorService.fetchFollowedProfessors(promise.data.professorsFollowed.map(p => p.professorId.id))
                    .then((promise) => {
                        this.setState({
                            followedProfessors: promise.data
                        });
                    });
            });
        },
        followProfessor: (professorId) => {
            StudentService.followProfessor(this.state.student.id.id, professorId).then((promise1) => {
                this.setState((prevState) => {
                    const newStudentProfessorsFollowedRef = [...prevState.studentProfessorsFollowed, promise1.data];
                    return {
                        studentProfessorsFollowed: newStudentProfessorsFollowedRef
                    }
                });
                ProfessorService.fetchById(promise1.data.professorId.id).then((promise2) => {
                    this.setState((prevState) => {
                        const newFollowedProfessorsRef = [...prevState.followedProfessors, promise2.data];
                        return {
                            followedProfessors: newFollowedProfessorsRef
                        }
                    });
                });
            });
        },
        unfollowProfessor: (professorId) => {
            StudentService.unfollowProfessor(this.state.student.id.id, professorId).then((promise) => {
                this.setState((prevState) => {
                    const newFollowedProfessorsRef = prevState.followedProfessors
                        .filter(p => p.id.id !== promise.data.professorId.id);
                    const newStudentConsultationSlotsRef =
                        prevState.studentProfessorsFollowed.filter(p => p.id.id !== promise.data.id.id);
                    return {
                        followedProfessors: newFollowedProfessorsRef,
                        studentProfessorsFollowed: newStudentConsultationSlotsRef
                    }
                })

            });
        },
        addStudentToConsultationSlot: (professorId, consultationSlotId, date, from, roomId, subjectId, note) => {
            StudentService
                .addToConsultationSlot(this.state.student.id.id, professorId, consultationSlotId, date, from, roomId,
                    subjectId, note).then((promise) => {
                        this.setState((prevState) => {
                            const newStudentConsultationSlotsRef =
                                [...prevState.studentConsultationSlots, promise.data];
                            return {
                                studentConsultationSlots: newStudentConsultationSlotsRef
                            }
                    });
                }).catch((err) => {
                    if (err.response.data.exception === "mk.ukim.finki.emt.consultations.studentmanagement.domain" +
                        ".model.exception.ConsultationSlotInProgressException") {
                        const message = "Консултацискиот термин е веќе започнат или завршен!";
                        this.setState({errorMessage: message});
                        this.handleShowErrorModal();
                    }
            });
        },
        removeStudentFromConsultationSlot: (professorId, consultationSlotId) => {
            StudentService.removeFromConsultationSlot(this.state.student.id.id, professorId, consultationSlotId)
                .then((promise) => {
                    this.setState((prevState) => {
                        const newStudentConsultationSlotsRef = prevState.studentConsultationSlots
                            .filter(cs => cs.id.id !== promise.data.id.id);
                        return {
                            studentConsultationSlots: newStudentConsultationSlotsRef
                        }
                    })
                }).catch((err) => {
                    if (err.response.data.exception === "mk.ukim.finki.emt.consultations.studentmanagement.domain" +
                        ".model.exception.ConsultationSlotInProgressException") {
                        const message = "Консултацискиот термин е веќе започнат или завршен!";
                        this.setState({errorMessage: message});
                        this.handleShowErrorModal();
                    }
            });
        }
    }

    RoomsApi = {
        loadRooms: () => {
            RoomService.fetchRooms().then((promise) => {
                const roomsByBuilding = {};
                promise.data.forEach(r => {
                    if (!roomsByBuilding[r.building]) {
                        roomsByBuilding[r.building] = []
                    }
                    roomsByBuilding[r.building].push(r);
                });
                this.setState({
                    roomsByBuilding: roomsByBuilding
                });
            });
        }
    }

    componentDidMount() {
        this.ProfessorsApi.loadProfessors();
        if (LocalStorageService.getRole() === "STUDENT") {
            this.StudentsApi.loadStudentByIndex(LocalStorageService.getIdentifier());
        }
        if (LocalStorageService.getRole() === "PROFESSOR") {
            this.ProfessorsApi.loadProfessorByUsername(LocalStorageService.getIdentifier());
        }
        this.RoomsApi.loadRooms();
    }

    render() {
        const routing = () => {
            const unregisteredUserRoutes = () => {
                return (
                    <>
                        <Route path={"/professors"} exact render={() =>
                            <Professors professors={this.state.professors}
                                        page={this.state.page}
                                        totalPages={this.state.totalPages}
                                        onPageClick={this.ProfessorsApi.loadProfessors}
                                        onSearch={this.ProfessorsApi.searchProfessors}
                                        followedProfessors={this.state.followedProfessors}
                                        studentProfessorsFollowed={this.state.studentProfessorsFollowed}
                                        onProfessorFollowed={this.StudentsApi.followProfessor}
                                        onProfessorUnfollowed={this.StudentsApi.unfollowProfessor}
                            />}>
                        </Route>
                        <Route path={"/professors/:professorId"} exact render={() =>
                            <Professor studentConsultationSlots={this.state.studentConsultationSlots}
                                       onStudentAddedToConsultationSlotSlot=
                                           {this.StudentsApi.addStudentToConsultationSlot}
                                       onStudentRemovedFromConsultationSlotSlot=
                                           {this.StudentsApi.removeStudentFromConsultationSlot}
                            />}>
                        </Route>
                        <Route path={"/rooms"} exact render={() =>
                            <Rooms roomsByBuilding={this.state.roomsByBuilding} />}>
                        </Route>
                        <Route path={"/login"} exact render={() =>
                            <Login />}>
                        </Route>
                        <Redirect to={"/professors"}/>
                    </>
                );
            }
            const studentRoutes = () => {
                if (LocalStorageService.getRole() === "STUDENT") {
                    return (
                        <>
                            <Route path={"/studentProfile"} exact render={() =>
                                <StudentProfile student={this.state.student}
                                                studentConsultationSlots={this.state.studentConsultationSlots}
                                                onStudentRemovedFromConsultationSlotSlot=
                                                    {this.StudentsApi.removeStudentFromConsultationSlot}
                                />}>
                            </Route>
                        </>
                    );
                }
            }
            const professorRoutes = () => {
                if (LocalStorageService.getRole() === "PROFESSOR") {
                    return (
                        <>
                            <Route path={"/professorProfile"} exact render={() =>
                                <ProfessorProfile professor={this.state.professor}
                                                  regularConsultationSlots={this.state.regularConsultationSlots}
                                                  additionalConsultationSlots={this.state.additionalConsultationSlots}
                                                  roomsByBuilding={this.state.roomsByBuilding}
                                                  onRegularConsultationSlotCreated=
                                                      {this.ProfessorsApi.createRegularConsultationSlot}
                                                  onRegularConsultationSlotUpdated=
                                                      {this.ProfessorsApi.updateRegularConsultationSlot}
                                                  onRegularConsultationSlotDeleted=
                                                      {this.ProfessorsApi.deleteRegularConsultationSlot}
                                                  onConsultationSlotCanceled={this.ProfessorsApi.cancelConsultationSlot}
                                                  onConsultationSlotUncanceled={this.ProfessorsApi.uncancelConsultationSlot}
                                                  onAdditionalConsultationSlotCreated=
                                                      {this.ProfessorsApi.createAdditionalConsultationSlot}
                                                  onAdditionalConsultationSlotUpdated=
                                                      {this.ProfessorsApi.updateAdditionalConsultationSlot}
                                                  onAdditionalConsultationSlotDeleted=
                                                      {this.ProfessorsApi.deleteAdditionalConsultationSlot}
                                />}>
                            </Route>
                            <Route path={"/consultationSlotStudents"} exact render={(props) =>
                                <ConsultationSlotStudents location={props.location}
                                                          professorSubjects={this.state.professor.subjects}
                                />}>
                            </Route>
                        </>
                    );
                }
            }

            return (
                <Router>
                    <Header loadProfessors={this.ProfessorsApi.loadProfessors}
                            loadProfessorByUsername={this.ProfessorsApi.loadProfessorByUsername}
                            loadStudent={this.StudentsApi.loadStudentByIndex} />

                    <section id="content" className="container my-5">
                        { unregisteredUserRoutes() }
                        { studentRoutes() }
                        { professorRoutes() }
                    </section>

                    <Footer />

                    <BackToTop />
                </Router>
            );
        }

        return (
            <div className="App">
                { routing() }
                {this.errorModal()}
            </div>
        );
    }

    handleCloseErrorModal = () => this.setState({showErrorModal: false});
    handleShowErrorModal = () => this.setState({showErrorModal: true});

    errorModal = () => {
        return (
            <Modal show={this.state.showErrorModal} onHide={this.handleCloseErrorModal} animation={false}>
                <Modal.Header closeButton>
                    <h5 className="mb-0">Грешка</h5>
                </Modal.Header>
                <Modal.Body>
                    <p className="text-break">
                        {this.state.errorMessage}
                    </p>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.handleCloseErrorModal}>
                        Во ред
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

}

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

export default App;
