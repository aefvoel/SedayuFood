package id.toriqwah.project.repository

interface UserService {

}

open class UserRepository(private val userService: UserService) {

}