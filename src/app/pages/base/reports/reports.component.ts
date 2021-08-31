import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
})
export class ReportsComponent implements OnInit {
  url: string =
    'https://i.postimg.cc/QCJnRkYt/Whats-App-Image-2021-08-30-at-6-31-19-PM.jpg';
  urlSafe: SafeResourceUrl;
  constructor(public sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.urlSafe = this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
  }
}
