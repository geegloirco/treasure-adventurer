import {Component, NgZone, OnInit, ViewChild} from '@angular/core';
import {ServerInfoService} from '../../../service/server-info/server-info.service';
import {PersonalityService, ServiceInitStatus} from '../../../service/personality/personality.service';

import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {MessageService} from '../../../service/message/message.service';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css']
})
export class HomeViewComponent implements OnInit {
  loadWaited = false;

  addresses = null;
  addressSelectedMap = {};
  selected = null;
  selectedOriginal = null;
  selectedLocation = {};

  constructor(
    private zone: NgZone,
    private modalService: NgbModal,
    private personalityService: PersonalityService,
    public serverInfo: ServerInfoService,
    public messageService: MessageService) {
  }

  ngOnInit() {
    this.personalityService.afterInitialized().subscribe(res => {
      if (res === ServiceInitStatus.successed) {
      }
    });
  }
}
