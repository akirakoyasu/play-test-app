import controllers.filters.AuthorizedFilter
import play.api.GlobalSettings
import play.api.mvc._

/**
 * Author: Akira Koyasu <mail@akirakoyasu.net>
 * Date: 2013/09/08
 * Time: 22:04
 */
object Global extends WithFilters(AuthorizedFilter) with GlobalSettings {}
