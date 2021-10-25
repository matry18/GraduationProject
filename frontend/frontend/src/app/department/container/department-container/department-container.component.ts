import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../../shared-services/authentication.service";

@Component({
  selector: 'app-department-container',
  templateUrl: './department-container.component.html',
  styleUrls: ['./department-container.component.css']
})
export class DepartmentContainerComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService) { }

  public ngOnInit(): void {
    this.authenticationService.redirectIfNotAuthenticated();
  }

}
