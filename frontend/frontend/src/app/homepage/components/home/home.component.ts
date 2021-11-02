import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../../shared-services/authorization.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(public authorizationService: AuthorizationService) { }

  public ngOnInit(): void {
  }

  introText = `POC stands for Proof Of Concept,
  and this system is therefore a proof of concept for the synchronizing mechanisms that could be implemented in the real Sensum system.`;
}
