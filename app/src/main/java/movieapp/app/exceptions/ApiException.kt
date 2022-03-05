package movieapp.app.exceptions

class ApiException(val errorCode: Int, errorMessage: String) : Exception(errorMessage)