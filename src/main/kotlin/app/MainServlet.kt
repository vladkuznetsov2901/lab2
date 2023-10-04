package app

import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import java.net.URLEncoder
import javax.servlet.ServletConfig
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.parsers.DocumentBuilderFactory

@WebServlet(urlPatterns = ["/first-page"])
class MainServlet : HttpServlet() {

    override fun init(config: ServletConfig?) {
        super.init(config)
    }

    override fun service(req: HttpServletRequest?, resp: HttpServletResponse?) {
        super.service(req, resp)
    }

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {

    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("login")
        val password = req?.getParameter("password")
        var answer = ""

        if (login != null) {
            if (password != null) {
                answer = checkPassword(login, password)
            }
        }

        resp?.sendRedirect("main.html?response=${URLEncoder.encode(answer, "UTF-8")}")


    }

    override fun destroy() {

    }

}

fun checkPassword(login: String, password: String): String {
    val xmlFilePath = "users.xml" // Замените на путь к вашему XML-файлу

    // Чтение XML из локального файла
    val xmlDocument = readXMLFromFile(xmlFilePath)

    // Поиск пользовательской записи по логину
    val userElement = findUserByLogin(xmlDocument, login)

    return if (userElement != null) {
        val storedPassword = userElement.getElementsByTagName("password").item(0).textContent

        if (password == storedPassword) {
            "Пароль верный. Доступ разрешен."
            // Здесь можно выполнить дополнительные действия для авторизованного пользователя
        } else {
            "Неверный пароль. Доступ запрещен."
        }
    } else {
        "Пользователь с таким логином не найден."
    }
}

fun readXMLFromFile(filePath: String): Document {
    val file = File(filePath)
    val factory = DocumentBuilderFactory.newInstance()
    val builder = factory.newDocumentBuilder()
    return builder.parse(file)
}

fun findUserByLogin(xmlDocument: Document, login: String): Element? {
    val userList = xmlDocument.getElementsByTagName("user")
    for (i in 0..<userList.length) {
        val userElement = userList.item(i) as Element
        val userLogin = userElement.getElementsByTagName("login").item(0).textContent
        if (userLogin == login) {
            return userElement
        }
    }
    return null
}

