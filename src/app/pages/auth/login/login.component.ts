import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthenticationService } from '../../../_services/auth/authentication.service';
import { UserService } from '../../../_services/auth/user.service';
import { AlertService } from '../../../_services/base/alert.service';

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
    // console.log(this.route.snapshot.paramMap.get('token'));
    // this.authenticationService.logout();
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
    this.spinner.hide();
    // this.authenticationService;
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
    this.router.navigateByUrl(`/authentication/${token}`);
  }
}
