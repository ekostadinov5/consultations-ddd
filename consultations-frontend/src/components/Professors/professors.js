import React from "react";
import ReactPaginate from 'react-paginate';
import ProfessorCard from "./ProfessorCard/professorCard";
import LocalStorageService from "../../service/localStorageService";

const Professors = (props) => {

    const followedProfessors = () => {
        return props.followedProfessors.map(professor =>
            <ProfessorCard key={professor.id.id}
                           value={professor}
                           studentProfessorsFollowed={props.studentProfessorsFollowed}
                           onProfessorFollowed={props.onProfessorFollowed}
                           onProfessorUnfollowed={props.onProfessorUnfollowed}/>);
    }

    const allProfessors = () => {
        return props.professors ? props.professors.map(professor =>
            <ProfessorCard key={professor.id.id}
                           value={professor}
                           studentProfessorsFollowed={props.studentProfessorsFollowed}
                           onProfessorFollowed={props.onProfessorFollowed}
                           onProfessorUnfollowed={props.onProfessorUnfollowed} />) : null;
    }

    const handlePageClick = (e) => {
        props.onPageClick(e.selected);
    }

    const pagination = () => {
        if (props.totalPages > 1) {
            return (
                <ReactPaginate previousLabel={"претходна"}
                               nextLabel={"следна"}
                               breakLabel={<span className="gap">...</span>}
                               breakClassName={"break-me"}
                               pageCount={props.totalPages}
                               marginPagesDisplayed={1}
                               pageRangeDisplayed={2}
                               pageClassName={"page-item"}
                               pageLinkClassName={"btn page-link"}
                               previousClassName={"page-item"}
                               nextClassName={"page-item"}
                               previousLinkClassName={"btn page-link"}
                               nextLinkClassName={"btn page-link"}
                               forcePage={props.page}
                               onPageChange={handlePageClick}
                               containerClassName={"pagination justify-content-center"}
                               activeClassName={"active"} />
            )
        }
    }

    const search = (e) => {
        props.onSearch(e.target.value);
    }

    const professorsSubNavigation = () => {
        if (LocalStorageService.getRole() === "STUDENT") {
            return (
                <>
                    <hr className="mb-0" />

                    <ul className="nav nav-tabs nav-fill mb-5" role="tablist">
                        <li className="nav-item">
                            <a className={`nav-link ${props.followedProfessors.length > 0 ? "active" : ""}`}
                               id="followedProfessorsTab" data-toggle="tab"
                               href="#followedProfessors" role="tab" aria-controls="followedProfessors"
                               aria-selected="true">
                                Следени
                            </a>
                        </li>
                        <li className="nav-item">
                            <a className={`nav-link ${props.followedProfessors.length === 0 ? "active" : ""}`}
                               id="allProfessorsTab" data-toggle="tab" href="#allProfessors" role="tab"
                               aria-controls="allProfessors" aria-selected="false">
                                Сите
                            </a>
                        </li>
                    </ul>
                </>
            );
        } else {
            return <hr />
        }
    }

    return (
        <>
            <form onSubmit={(e) => e.preventDefault()} className="form-inline my-2 my-lg-0">
                <p className="lead d-inline-block m-auto m-sm-0">
                    Наставен кадар
                </p>
                <input className="form-control ml-auto mt-3 mt-sm-0" type="search"
                       placeholder="Филтер..." aria-label="Search" onChange={search} />
            </form>

            { professorsSubNavigation() }

            <div className="tab-content">
                <div className={`tab-pane fade ${props.followedProfessors.length > 0 ? "show active" : ""}`}
                     id="followedProfessors" role="tabpanel" aria-labelledby="followedProfessorsTab">
                    <div className="row mb-4">
                        { followedProfessors() }
                    </div>
                </div>
                <div className={`tab-pane fade ${props.followedProfessors.length === 0 ? "show active" : ""}`}
                     id="allProfessors" role="tabpanel" aria-labelledby="allProfessorsTab">
                    <div className="row mb-4">
                        { allProfessors() }
                    </div>
                    { pagination() }
                </div>
            </div>
        </>
    );
}

export default Professors;
