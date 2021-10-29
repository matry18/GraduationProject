import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthenticationService} from "./authentication.service";
import {AuthorizationService} from "./authorization.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationRouteGuardService implements CanActivate {

  constructor(private authenticationService: AuthenticationService,
              private authorizationService: AuthorizationService) {
  }

  public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.authenticationService.redirectIfNotAuthenticated();
    if(route.data.accessRights) {
      return  this.authenticationService.isAuthenticated() && this.authorizationService.hasAccessRight(route.data.accessRights);
    } else {
      return this.authenticationService.isAuthenticated();
    }

  }
}
