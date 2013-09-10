package models

/**
 * Author: Akira Koyasu <mail@akirakoyasu.net>
 */
case class Pref(name: String, kana: String)

object Pref {
  lazy val table = Map(
    "hokkaido" -> Pref("hokkaido", "北海道"),
    "aomori"   -> Pref("aomori", "青森"),
    "tokyo"    -> Pref("tokyo", "東京")
  )

  def byName(name: String) = table.get(name)
}