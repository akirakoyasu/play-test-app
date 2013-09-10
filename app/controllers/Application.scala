package controllers

import play.api.mvc._
import models.{City, Pref}

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def pref(prefName: String) = Action {
    Pref.byName(prefName) match {
      case Some(pref) =>  Ok(views.html.pref(pref))
      case _ => NotFound
    }
  }
  def city(prefName: String, cityName: String) = Action {
    (Pref.byName(prefName), City.byName(cityName)) match {
      case (Some(pref), Some(city)) =>  Ok(views.html.city(pref, city))
      case _ => NotFound
    }
  }
  def list = TODO
}