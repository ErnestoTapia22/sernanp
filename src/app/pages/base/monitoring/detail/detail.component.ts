import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ResizeMapDirective } from '../../../_directives/resize-map.directive';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css'],
})
export class DetailComponent implements OnInit {
  modalRef: NgbModalRef;
  @ViewChild('ResizeMapDirective') appResizeMap;
  mapProperties: any;
  mapViewProperties: any;
  constructor(
    private activatedRoute: ActivatedRoute,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    // console.log(this.activatedRoute.snapshot.paramMap.get('id'));
    this.mapProperties = {
      basemap: 'hybrid',
      ground: 'world-elevation',
    };
    this.mapViewProperties = {
      center: [-75.744, -8.9212],
      zoom: 9,
    };
    // this.appResizeMap.appResizeMap();
  }
  onMapInit({ map, view }) {}
  onDeleteModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
  }
  initMonitoring() {}
  onInitModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
  }
  deleteMonitoring() {}
}
