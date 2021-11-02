import { Component, OnInit } from '@angular/core';
import {AccessRightDto} from "../../../typings";
import {AccessRightService} from "../../../access-rights/access-right.service";

@Component({
  selector: 'role-container',
  templateUrl: './role-container.component.html',
  styleUrls: ['./role-container.component.css']
})
export class RoleContainerComponent implements OnInit {

  public accessRights: AccessRightDto[] = [];

  constructor(private accessRightService: AccessRightService) { }

  public ngOnInit(): void {
    this.fetchAccessRights();
  }

  private fetchAccessRights(): void {
    this.accessRightService.getAllAccessRights().subscribe((accessRights: AccessRightDto[]) => {
      this.accessRights = accessRights;
    });
  }

}
