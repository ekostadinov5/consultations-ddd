// import React, { useState } from "react";
// import UsersService from "../../repository/axiosUserRepository";
// import LocalStorageService from "../../service/localStorageService";
//
// const Login = () => {
//
//     const [errorMessage, setErrorMessage] = useState("");
//
//     const onSubmit = (e) => {
//         e.preventDefault();
//         UsersService.login(e.target.username.value, e.target.password.value).then((promise) => {
//             LocalStorageService.setToken(promise.headers.authorization);
//             LocalStorageService.setIdentifier(promise.headers.identifier);
//             LocalStorageService.setRole(promise.headers.role);
//             window.location.href = '/';
//         }).catch((error) => {
//             if(error.response && error.response.status === 403) {
//                 setErrorMessage('Корисничкото име или лозинката кои ги внесовте се невалидни.');
//             }
//         });
//     }
//
//     return (
//         <form onSubmit={onSubmit}>
//             <div className="row">
//                 <div className="col-12 col-sm-9 col-md-7 col-lg-5 m-auto">
//                     <div className="card">
//                         <div className="card-body py-5">
//                             <div className="form-group">
//                                 <label htmlFor="username">Корисничко име</label>
//                                 <input id="username" type="text" className="form-control"/>
//                             </div>
//                             <div className="form-group">
//                                 <label htmlFor="password">Лозинка</label>
//                                 <input id="password" type="password" className="form-control"/>
//                             </div>
//                             <div className="text-danger small">
//                                 {errorMessage}
//                             </div>
//                             <button className="btn btn-success btn-block mt-5">Најави се</button>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//         </form>
//     );
// }
//
// export default Login;
