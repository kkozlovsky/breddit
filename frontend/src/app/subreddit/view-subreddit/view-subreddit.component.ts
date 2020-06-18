import {Component, OnInit} from '@angular/core';
import {PostService} from "../../shared/post.service";
import {ActivatedRoute} from "@angular/router";
import {PostModel} from "../../shared/post-model";
import {SubredditService} from "../subreddit.service";
import {SubredditModel} from "../subreddit-response";

@Component({
  selector: 'app-view-subreddit',
  templateUrl: './view-subreddit.component.html',
  styleUrls: ['./view-subreddit.component.css']
})
export class ViewSubredditComponent implements OnInit {

  id: number;
  posts: PostModel[];
  postLength: number;
  subreddit: SubredditModel

  constructor(private activatedRoute: ActivatedRoute,
              private postService: PostService,
              private subredditService: SubredditService
  ) {
  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params.id;

    this.subredditService.getSubredditById(this.id).subscribe(data => {
      console.log(data)
      this.subreddit = data
    })

    this.postService.getAllPostsBySubredditId(this.id).subscribe(data => {
      this.posts = data;
      this.postLength = data.length;
    });
  }

}
