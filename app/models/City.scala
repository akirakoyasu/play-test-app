package models

/**
 * Author: Akira Koyasu <mail@akirakoyasu.net>
 */
case class City(name: String, kana: String)

object City {
  lazy val table = Map(
    "chiyodaku" -> City("chiyodaku", "千代田区")
  )

  def byName(name: String) = table.get(name)
}