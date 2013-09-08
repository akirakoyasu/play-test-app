package controllers.filters

import play.api.mvc.{Results, Result, RequestHeader, Filter}
import play.api.Play
import org.apache.commons.codec.binary.Base64
import com.google.common.base.Charsets
import com.google.common.net.HttpHeaders
import play.api.Play.current

/**
 * Author: Akira Koyasu <mail@akirakoyasu.net>
 * Date: 2013/09/08
 * Time: 23:53
 */
object AuthorizedFilter extends Filter {

  private lazy val delegate =
    if (authorizationRequired())
      BasicAuthFilter
    else
      DoNothingFilter

  override def apply(next: RequestHeader => Result)(request: RequestHeader): Result
  = delegate.apply(next)(request)

  private def authorizationRequired(): Boolean = {
    val config = Play.configuration
    config.getBoolean("auth.required").getOrElse(false)
  }
}

object BasicAuthFilter extends Filter {
  private val REALM = "Basic realm=\"Enter your login ID and password, please.\""
  private val USER = "user"
  private val PASSWORD = "password"

  private lazy val AUTH_TOKEN = {
    val credentials = USER + ":" + PASSWORD
    val encodedBytes = Base64.encodeBase64(credentials.getBytes(Charsets.ISO_8859_1))
    val encoded = new String(encodedBytes, Charsets.ISO_8859_1)
    "Basic " + encoded
  }

  override def apply(next: RequestHeader => Result)(request: RequestHeader): Result = {
    val authHeader = request.headers.get(HttpHeaders.AUTHORIZATION)

    if (authHeader.isEmpty) {
      return Results.Unauthorized
        .withHeaders((HttpHeaders.WWW_AUTHENTICATE, REALM))
    }

    if (authHeader.get == AUTH_TOKEN) {
      next(request)
    } else {
      Results.Unauthorized
    }
  }
}

object DoNothingFilter extends Filter {
  override def apply(next: (RequestHeader) => Result)(request: RequestHeader): Result = next(request)
}