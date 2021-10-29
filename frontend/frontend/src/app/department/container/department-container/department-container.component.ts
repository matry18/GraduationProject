import {Component, OnInit} from '@angular/core';
import {AuthorizationService} from "../../../shared-services/authorization.service";

@Component({
  selector: 'app-department-container',
  templateUrl: './department-container.component.html',
  styleUrls: ['./department-container.component.css']
})
export class DepartmentContainerComponent implements OnInit {

  constructor(public authorizationService: AuthorizationService) {
  }

  public ngOnInit(): void {
  }

}
