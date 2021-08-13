import {
  Directive,
  Input,
  AfterViewInit,
  OnDestroy,
  ElementRef,
  Renderer2,
  HostBinding,
} from '@angular/core';
import { fromEvent, Subscription } from 'rxjs';
import { debounceTime, throttleTime } from 'rxjs/operators';

@Directive({
  selector: '[appResizeMap]',
})
export class ResizeMapDirective implements AfterViewInit, OnDestroy {
  @Input('appResizeMap') topOffset: number;
  @Input() minHeight: number;
  @Input() maxHeight?: number = 0;
  // @HostBinding('style.overflow-y') overflowY = 'auto';

  private doomElement: HTMLElement;
  private resizeSub: Subscription;

  constructor(private renderer: Renderer2, private elementRef: ElementRef) {
    this.doomElement = this.elementRef.nativeElement as HTMLElement;
    this.resizeSub = fromEvent(window, 'resize')
      .pipe(throttleTime(500), debounceTime(500))
      .subscribe(() => {
        this.setHeight();
      });
  }
  ngAfterViewInit() {
    this.setHeight();
  }
  ngOnDestroy() {
    this.resizeSub.unsubscribe();
  }
  setHeight() {
    const parentHeight = this.doomElement.parentElement.clientHeight;
    const windowHeight = window?.innerHeight;
    const topOffSet = this.topOffset || 57;
    let height = windowHeight - topOffSet;
    if (this.minHeight && height < this.minHeight) {
      height = this.minHeight;
    }

    if (this.maxHeight !== undefined && this.maxHeight !== 0) {
      this.renderer.setStyle(
        this.doomElement,
        'max-height',
        `${this.maxHeight}px`
      );
      this.renderer.setStyle(this.doomElement, 'height', `${height}px`);
    } else {
      this.renderer.setStyle(this.doomElement, 'height', `${height}px`);
    }
  }
  private calcTopOffset(): number {
    try {
      const rect = this.doomElement.getBoundingClientRect();
      const scrollTop =
        window.pageYOffset || document.documentElement.scrollTop;

      return rect.top + scrollTop;
    } catch (e) {
      return 0;
    }
  }
}
