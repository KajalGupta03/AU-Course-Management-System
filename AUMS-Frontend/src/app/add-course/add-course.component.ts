import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AllcoursesService } from '../services/allcourses.service';
import { SkillService } from '../services/skill.service';
import { Router } from '@angular/router';
import {MaterialService} from './../services/material.service';
import { LoginserviceService } from "../services/loginservice.service"

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {
  data: any;
  openmaterial: boolean = false;
  openskill: boolean = false;
  list: any = [];
  skills: any = [];
  checked: boolean = false;
  courseid: any;
  materialObject: any = {};
  user: any;
  dialogformgroup!: FormGroup;

  constructor(
    private service: AllcoursesService,
    private skillservice: SkillService,
    private router: Router,
    private materialService: MaterialService,
    private loginservice: LoginserviceService
  ) { }

  

  materialformgroup = new FormGroup({
    materialDescription: new FormControl('', Validators.required),
    fileType: new FormControl('', Validators.required),
    file: new FormControl(null, Validators.required),
  });

  ngOnInit(): void {
    this.user = this.loginservice.getUser();
    this.user = JSON.parse(this.user);
    console.log(this.user);
    this.dialogformgroup = new FormGroup({
      courseDescription: new FormControl('', Validators.required),
      courseName: new FormControl('', Validators.required),
      prerequisite: new FormControl('', Validators.required),
      userId: new FormControl({ value: this.user['userId'], disabled: true }),
      imageUrl: new FormControl('', Validators.required),
    });
  }

  addcourse(): void {
    if (this.dialogformgroup.valid) {
      this.service
        .addCourse(this.dialogformgroup.getRawValue())
        .subscribe((response1) => {
          this.courseid = response1.courseId;
        });
      this.openmaterial = true;
      this.skillservice.getAllSkills().subscribe((response) => {
        console.log(response);
        this.list = response;
      });
    } else {
      
      this.close();
    }
    // this.dialogRef.close();
  }

  addmaterialandskill(): void {
    
    //call api for adding material

    this.materialObject.courseId = this.courseid;
    this.materialObject.fileType = this.materialformgroup.controls[
      'fileType'
    ].value;
    this.materialObject.file = this.materialformgroup.controls[
      'file'
    ].value._files;
    this.materialObject.materialDescription = this.materialformgroup.controls[
      'materialDescription'
    ].value;
    console.log(this.materialObject);
    this.materialService
      .addmaterial(this.materialObject)
      .subscribe((response2) => {
        console.log(response2);
      });

    console.log('inside material');
    this.openskill = true;
    console.log(this.skills);
  }

  addskills(ischecked: boolean, skillid: any): void {
    if (ischecked == true) {
      this.skills.push(skillid);
    } else {
      this.skills = this.skills.filter(function (dataitem: any) {
        return dataitem !== skillid;
      });
    }
    console.log(this.skills);
  }

  addfinally(): void {
    for (var i of this.skills) {
      this.skillservice
        .addSkillInCourse(this.courseid, i)
        .subscribe((response: boolean) => {
          console.log(response);
        });
    }
    this.router.navigate(['home']);
    // this.dialogRef.close();
    
  }

  close(): void {
    this.router.navigate(['home']);
    // this.dialogRef.close();
  }
}
