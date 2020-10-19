
const LocalStorageService = (() => {

    let _service;

    const _getService = () => {
        if (_service) {
            _service = this;
        }
        return _service
    };

    const _setToken = (token) => {
        localStorage.setItem('access_token_consultations', token);
    };

    const _getToken = () => {
        return localStorage.getItem('access_token_consultations');
    };

    const _clearToken = () => {
        localStorage.removeItem('access_token_consultations');
    };

    const _setRole = (role) => {
        localStorage.setItem('role_consultations', role);
    };

    const _getRole = () => {
        return localStorage.getItem('role_consultations');
    };

    const _clearRole = () => {
        localStorage.removeItem('role_consultations');
    };

    const _setIdentifier = (identifier) => {
        localStorage.setItem('identifier_consultations', identifier);
    };

    const _getIdentifier = () => {
        return localStorage.getItem('identifier_consultations');
    };

    const _clearIdentifier = () => {
        localStorage.removeItem('identifier_consultations');
    };

    return {
        getService: _getService,
        setToken: _setToken,
        getToken: _getToken,
        clearToken: _clearToken,
        setRole: _setRole,
        getRole: _getRole,
        clearRole: _clearRole,
        setIdentifier: _setIdentifier,
        getIdentifier: _getIdentifier,
        clearIdentifier: _clearIdentifier
    };

})();

export default LocalStorageService;
