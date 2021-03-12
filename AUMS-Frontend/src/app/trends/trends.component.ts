import { Component, OnInit } from '@angular/core';
import { TrendsService } from '../services/trends.service'

@Component({
  selector: 'app-trends',
  templateUrl: './trends.component.html',
  styleUrls: ['./trends.component.css']
})
export class TrendsComponent implements OnInit {

  pieChartType:any = 'pie';
  skillName:any=[];
  skillValue:any=[];
  skillTrends:any;

  courseName:any=[];
  ratingValue:any=[];
  courseTrends:any;


  public chartColors: Array<any> = [
    {
      backgroundColor: ['#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
      hoverBackgroundColor: ['#FF5A5E', '#5AD3D1', '#FFC870', '#A8B3C5', '#616774'],
      borderWidth: 2,
    }
  ];
  

  constructor(private trendsService:TrendsService) { }

  ngOnInit(): void {

     this.trendsService.getTrendingSkills().subscribe((response:any)=>{
      console.log(response);
      this.skillTrends = response;
      for(let i of this.skillTrends){
        this.skillName.push(i.name);
        this.skillValue.push(i.value);
      }
      console.log(this.skillName);
    })


    this.trendsService.getCourseRating().subscribe((response:any)=>{
      console.log(response);
      this.courseTrends = response;
      for(let i of this.courseTrends){
        this.courseName.push(i.name);
        this.ratingValue.push(i.value);
      }
    })

  }

}
