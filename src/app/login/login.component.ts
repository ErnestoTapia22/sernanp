import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../_services/alert.service';

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
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.required],
    });
  }
  get f() {
    return this.loginForm.controls;
  }
  onSubmit() {
    this.submitted = true;
    // this.spinner.show();
    // if (this.loginForm.invalid) {
    //     this.spinner.hide();
    //     return;
    // }
    // this.authenticationService.login(this.f.email.value, this.f.password.value)
    //     .pipe(first())
    //     .subscribe(
    //       data => {
    //           var obj = data;
    //           if (obj.hasOwnProperty("error_description")) {
    //             this.spinner.hide();
    //             this.error = data;
    //             this.alertService.error(this.error.error_description);
    //           } else {
    //             this.token = data;
    //             this.onStoreGetDataUser(this.token.access_token);
    //           }

    //         },
    //         error => {
    //             this.spinner.hide();
    //             this.alertService.error("Error de servicio en el sistema.");

    //         });
  }
  //   login(email, password) {
  //     this.authenticationService.login(email, password)
  //         .pipe(first())
  //         .subscribe(
  //             data => {
  //                 this.token = data;
  //                 this.onStoreGetDataUser(this.token.access_token);
  //             },
  //             error => {
  //                 this.spinner.hide();
  //                 this.alertService.error(error.error.error_description);
  //             });
  // }

  // onStoreGetDataUser(token: string) {
  //   this.userService.getUserData(token)
  //       .pipe(first())
  //       .subscribe((data: any) => {
  //           if (data.rolName.toLowerCase() === 'reclutador') {
  //               this.router.navigateByUrl('/reclutador/avisos');
  //           } else if(data.rolName.toLowerCase() === 'admin'){
  //               this.router.navigateByUrl('/admin/usuario');
  //           }else{
  //               this.onStoreDataPostulanteResumen(this.token.access_token);
  //           }
  //       });
  //}
}
