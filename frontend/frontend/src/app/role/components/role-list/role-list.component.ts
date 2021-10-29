import {Component, Input, OnInit} from '@angular/core';
import {AccessRightDto, RoleDto} from "../../../typings";
import {RoleService} from "../../role.service";
import {MatDialog} from "@angular/material/dialog";
import {CreateRoleComponent} from "../create-role/create-role.component";
import {SnackbarService} from "../../../snackbars/FormSubmission/snackbar.service";
import {AuthorizationService} from "../../../shared-services/authorization.service";

@Component({
  selector: 'role-list',
  templateUrl: './role-list.component.html',
  styleUrls: ['./role-list.component.css']
})
export class RoleListComponent implements OnInit {

  @Input() public accessRights: AccessRightDto[] = []; //used when creating roles
  public roles: RoleDto[] = [];

  public displayedColumns = ['name', 'accessRights'];

  constructor(private roleService: RoleService,
              private dialog: MatDialog,
              private snackbarService: SnackbarService,
              public authorizationService: AuthorizationService) {
  }

  public ngOnInit(): void {
    this.fetchAllRoles();
  }

  public fetchAllRoles(): void {
    this.roleService.getAllRoles().subscribe((roles: RoleDto[]) => {
      this.roles = roles;
    });
  }

  public getAccessRightString(accessRights: AccessRightDto[]): string {
    let accessRightString = "";
    for (let i = 0; i < accessRights.length; i++) {
      if (i == accessRights.length - 1) {
        accessRightString = accessRightString.concat(accessRights[i].name);
      } else {
        accessRightString = accessRightString.concat(accessRights[i].name + ", ");
      }
    }
    return accessRightString;
  }

  public deleteRole(role: RoleDto): void {
    if (!this.authorizationService.hasAccessRight(['admin', 'delete'])) {
      return;
    }
    this.roleService.deleteRole(role.id)
      .subscribe(() => {
        this.snackbarService.openSuccessfulSnackBar();
        this.fetchAllRoles();
      }, () => {
        this.snackbarService.openFailedSnackBar();
      });
  }

  public openEditDialog(role: RoleDto): void {
    if (!this.authorizationService.hasAccessRight(['admin', 'update'])) {
      return;
    }
    this.dialog.open(CreateRoleComponent, {
      data: {roleDto: role}
    });
  }

}
