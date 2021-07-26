import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../_services/alert.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthenticationService } from '../../_services/authentication.service';
import { first } from 'rxjs/operators';
import { UserService } from '../../_services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  returnUrl: string;
  token: any;
  error: any;
  userData: any;
  customMessage: string = null;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService,
    private spinner: NgxSpinnerService,
    private authenticationService: AuthenticationService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      user: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required],
    });
    this.authenticationService.logout();
  }
  get f() {
    return this.loginForm.controls;
  }
  onSubmit() {
    this.submitted = true;
    this.spinner.show();
    if (this.loginForm.invalid) {
      this.spinner.hide();
      return;
    }
    this.authenticationService;
    // .login(this.f.user.value, this.f.password.value)
    // .pipe(first())
    // .subscribe(
    //   (data) => {
    //     var obj = data;
    //     if (obj.hasOwnProperty('error_description')) {
    //       this.spinner.hide();
    //       this.error = data;
    //       this.alertService.error(this.error.error_description);
    //     } else {
    //       this.token = data;
    //       this.onStoreGetDataUser(this.token.access_token);
    //     }
    //   },
    //   (error) => {
    //     this.spinner.hide();
    //     this.alertService.error('Error de servicio en el sistema.');
    //   }
    // );
    this.onStoreGetDataUser('randomToken');
  }

  onStoreGetDataUser(token: string) {
    // this.userService
    //   .getUserData(token)
    //   .pipe(first())
    //   .subscribe((data: any) => {
    //     if (data.rolName.toLowerCase() === 'admin') {
    //       this.router.navigateByUrl('/map/visor');
    //     } else if (data.rolName.toLowerCase() === 'visor') {
    //       //this.router.navigateByUrl('/admin/usuario');
    //     }
    //   });
    this.router.navigateByUrl('/map/visor');
  }
}
