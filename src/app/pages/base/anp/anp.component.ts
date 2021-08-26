import { Component, OnInit } from '@angular/core';
import { AnpService } from '../../../_services/base/anp.service';

@Component({
  selector: 'app-anp',
  templateUrl: './anp.component.html',
  styleUrls: ['./anp.component.css'],
})
export class AnpComponent implements OnInit {
  pageSize: Number = 0;
  page: Number = 0;
  total: Number = 0;
  anpList: any[];
  constructor(private anpService: AnpService) {}

  ngOnInit(): void {
    this.listAnp();
  }
  getPage(e) {}
  onChangePageSize(e) {}
  listAnp() {
    this.anpService.agreementList().subscribe((response) => {
      this.anpList = response;
    });
  }
}
