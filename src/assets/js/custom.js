!(function (t, e) {
  "object" == typeof exports && "undefined" != typeof module
    ? (module.exports = e())
    : "function" == typeof define && define.amd
    ? define(e)
    : ((t = t || self).PerfectScrollbar = e());
})(this, function () {
  "use strict";
  var p = Math.abs,
    f = Math.floor;
  function b(t) {
    return getComputedStyle(t);
  }
  function a(t, e) {
    for (var i in e) {
      var l = e[i];
      "number" == typeof l && (l += "px"), (t.style[i] = l);
    }
    return t;
  }
  function c(t) {
    var e = document.createElement("div");
    return (e.className = t), e;
  }
  function s(t, e) {
    if (!l) throw new Error("No element matching method supported");
    return l.call(t, e);
  }
  function n(t) {
    t.remove ? t.remove() : t.parentNode && t.parentNode.removeChild(t);
  }
  function o(t, e) {
    return Array.prototype.filter.call(t.children, function (t) {
      return s(t, e);
    });
  }
  function v(t, e) {
    var i = t.element.classList,
      t = w.state.scrolling(e);
    i.contains(t) ? clearTimeout(r[e]) : i.add(t);
  }
  function Y(t, e) {
    r[e] = setTimeout(function () {
      return t.isAlive && t.element.classList.remove(w.state.scrolling(e));
    }, t.settings.scrollingThreshold);
  }
  function u(t) {
    if ("function" == typeof window.CustomEvent) return new CustomEvent(t);
    var e = document.createEvent("CustomEvent");
    return e.initCustomEvent(t, !1, !1, void 0), e;
  }
  function t(t, e, i, l, r) {
    var n;
    if ((void 0 === l && (l = !0), void 0 === r && (r = !1), "top" === e))
      n = ["contentHeight", "containerHeight", "scrollTop", "y", "up", "down"];
    else {
      if ("left" !== e) throw new Error("A proper axis should be provided");
      n = [
        "contentWidth",
        "containerWidth",
        "scrollLeft",
        "x",
        "left",
        "right",
      ];
    }
    !(function (t, e, i, l, r) {
      var n = i[0],
        o = i[1],
        s = i[2],
        a = i[3],
        c = i[4],
        h = i[5];
      void 0 === l && (l = !0), void 0 === r && (r = !1);
      i = t.element;
      (t.reach[a] = null),
        i[s] < 1 && (t.reach[a] = "start"),
        i[s] > t[n] - t[o] - 1 && (t.reach[a] = "end"),
        e &&
          (i.dispatchEvent(u("ps-scroll-" + a)),
          e < 0
            ? i.dispatchEvent(u("ps-scroll-" + c))
            : 0 < e && i.dispatchEvent(u("ps-scroll-" + h)),
          l &&
            (function (t, e) {
              v(t, e), Y(t, e);
            })(t, a)),
        t.reach[a] &&
          (e || r) &&
          i.dispatchEvent(u("ps-" + a + "-reach-" + t.reach[a]));
    })(t, i, n, l, r);
  }
  function h(t) {
    return parseInt(t, 10) || 0;
  }
  function X(t) {
    var e = Math.round,
      i = t.element,
      l = f(i.scrollTop),
      r = i.getBoundingClientRect();
    (t.containerWidth = e(r.width)),
      (t.containerHeight = e(r.height)),
      (t.contentWidth = i.scrollWidth),
      (t.contentHeight = i.scrollHeight),
      i.contains(t.scrollbarXRail) ||
        (o(i, w.element.rail("x")).forEach(n), i.appendChild(t.scrollbarXRail)),
      i.contains(t.scrollbarYRail) ||
        (o(i, w.element.rail("y")).forEach(n), i.appendChild(t.scrollbarYRail)),
      !t.settings.suppressScrollX &&
      t.containerWidth + t.settings.scrollXMarginOffset < t.contentWidth
        ? ((t.scrollbarXActive = !0),
          (t.railXWidth = t.containerWidth - t.railXMarginWidth),
          (t.railXRatio = t.containerWidth / t.railXWidth),
          (t.scrollbarXWidth = d(
            t,
            h((t.railXWidth * t.containerWidth) / t.contentWidth)
          )),
          (t.scrollbarXLeft = h(
            ((t.negativeScrollAdjustment + i.scrollLeft) *
              (t.railXWidth - t.scrollbarXWidth)) /
              (t.contentWidth - t.containerWidth)
          )))
        : (t.scrollbarXActive = !1),
      !t.settings.suppressScrollY &&
      t.containerHeight + t.settings.scrollYMarginOffset < t.contentHeight
        ? ((t.scrollbarYActive = !0),
          (t.railYHeight = t.containerHeight - t.railYMarginHeight),
          (t.railYRatio = t.containerHeight / t.railYHeight),
          (t.scrollbarYHeight = d(
            t,
            h((t.railYHeight * t.containerHeight) / t.contentHeight)
          )),
          (t.scrollbarYTop = h(
            (l * (t.railYHeight - t.scrollbarYHeight)) /
              (t.contentHeight - t.containerHeight)
          )))
        : (t.scrollbarYActive = !1),
      t.scrollbarXLeft >= t.railXWidth - t.scrollbarXWidth &&
        (t.scrollbarXLeft = t.railXWidth - t.scrollbarXWidth),
      t.scrollbarYTop >= t.railYHeight - t.scrollbarYHeight &&
        (t.scrollbarYTop = t.railYHeight - t.scrollbarYHeight),
      (function (t, e) {
        var i = { width: e.railXWidth },
          l = f(t.scrollTop);
        (i.left = e.isRtl
          ? e.negativeScrollAdjustment +
            t.scrollLeft +
            e.containerWidth -
            e.contentWidth
          : t.scrollLeft),
          e.isScrollbarXUsingBottom
            ? (i.bottom = e.scrollbarXBottom - l)
            : (i.top = e.scrollbarXTop + l),
          a(e.scrollbarXRail, i);
        l = { top: l, height: e.railYHeight };
        e.isScrollbarYUsingRight
          ? e.isRtl
            ? (l.right =
                e.contentWidth -
                (e.negativeScrollAdjustment + t.scrollLeft) -
                e.scrollbarYRight -
                e.scrollbarYOuterWidth -
                9)
            : (l.right = e.scrollbarYRight - t.scrollLeft)
          : e.isRtl
          ? (l.left =
              e.negativeScrollAdjustment +
              t.scrollLeft +
              2 * e.containerWidth -
              e.contentWidth -
              e.scrollbarYLeft -
              e.scrollbarYOuterWidth)
          : (l.left = e.scrollbarYLeft + t.scrollLeft),
          a(e.scrollbarYRail, l),
          a(e.scrollbarX, {
            left: e.scrollbarXLeft,
            width: e.scrollbarXWidth - e.railBorderXWidth,
          }),
          a(e.scrollbarY, {
            top: e.scrollbarYTop,
            height: e.scrollbarYHeight - e.railBorderYWidth,
          });
      })(i, t),
      t.scrollbarXActive
        ? i.classList.add(w.state.active("x"))
        : (i.classList.remove(w.state.active("x")),
          (t.scrollbarXWidth = 0),
          (t.scrollbarXLeft = 0),
          (i.scrollLeft = !0 === t.isRtl ? t.contentWidth : 0)),
      t.scrollbarYActive
        ? i.classList.add(w.state.active("y"))
        : (i.classList.remove(w.state.active("y")),
          (t.scrollbarYHeight = 0),
          (t.scrollbarYTop = 0),
          (i.scrollTop = 0));
  }
  function d(t, e) {
    var i = Math.min,
      l = Math.max;
    return (
      t.settings.minScrollbarLength &&
        (e = l(e, t.settings.minScrollbarLength)),
      (e = t.settings.maxScrollbarLength
        ? i(e, t.settings.maxScrollbarLength)
        : e)
    );
  }
  function e(i, t) {
    function l(t) {
      t.touches && t.touches[0] && (t[s] = t.touches[0].pageY),
        (f[u] = b + m * (t[s] - g)),
        v(i, d),
        X(i),
        t.stopPropagation(),
        t.preventDefault();
    }
    function r() {
      Y(i, d),
        i[p].classList.remove(w.state.clicking),
        i.event.unbind(i.ownerDocument, "mousemove", l);
    }
    function e(t, e) {
      (b = f[u]),
        e && t.touches && (t[s] = t.touches[0].pageY),
        (g = t[s]),
        (m = (i[o] - i[n]) / (i[a] - i[h])),
        e
          ? i.event.bind(i.ownerDocument, "touchmove", l)
          : (i.event.bind(i.ownerDocument, "mousemove", l),
            i.event.once(i.ownerDocument, "mouseup", r),
            t.preventDefault()),
        i[p].classList.add(w.state.clicking),
        t.stopPropagation();
    }
    var n = t[0],
      o = t[1],
      s = t[2],
      a = t[3],
      c = t[4],
      h = t[5],
      u = t[6],
      d = t[7],
      p = t[8],
      f = i.element,
      b = null,
      g = null,
      m = null;
    i.event.bind(i[c], "mousedown", function (t) {
      e(t);
    }),
      i.event.bind(i[c], "touchstart", function (t) {
        e(t, !0);
      });
  }
  function i(t) {
    (this.element = t), (this.handlers = {});
  }
  var l =
      "undefined" != typeof Element &&
      (Element.prototype.matches ||
        Element.prototype.webkitMatchesSelector ||
        Element.prototype.mozMatchesSelector ||
        Element.prototype.msMatchesSelector),
    w = {
      main: "ps",
      rtl: "ps__rtl",
      element: {
        thumb: function (t) {
          return "ps__thumb-" + t;
        },
        rail: function (t) {
          return "ps__rail-" + t;
        },
        consuming: "ps__child--consume",
      },
      state: {
        focus: "ps--focus",
        clicking: "ps--clicking",
        active: function (t) {
          return "ps--active-" + t;
        },
        scrolling: function (t) {
          return "ps--scrolling-" + t;
        },
      },
    },
    r = { x: null, y: null },
    g = { isEmpty: { configurable: !0 } };
  (i.prototype.bind = function (t, e) {
    void 0 === this.handlers[t] && (this.handlers[t] = []),
      this.handlers[t].push(e),
      this.element.addEventListener(t, e, !1);
  }),
    (i.prototype.unbind = function (e, i) {
      var l = this;
      this.handlers[e] = this.handlers[e].filter(function (t) {
        return (
          !(!i || t === i) || (l.element.removeEventListener(e, t, !1), !1)
        );
      });
    }),
    (i.prototype.unbindAll = function () {
      for (var t in this.handlers) this.unbind(t);
    }),
    (g.isEmpty.get = function () {
      var e = this;
      return Object.keys(this.handlers).every(function (t) {
        return 0 === e.handlers[t].length;
      });
    }),
    Object.defineProperties(i.prototype, g);
  function m() {
    this.eventElements = [];
  }
  (m.prototype.eventElement = function (e) {
    var t = this.eventElements.filter(function (t) {
      return t.element === e;
    })[0];
    return t || ((t = new i(e)), this.eventElements.push(t)), t;
  }),
    (m.prototype.bind = function (t, e, i) {
      this.eventElement(t).bind(e, i);
    }),
    (m.prototype.unbind = function (t, e, i) {
      t = this.eventElement(t);
      t.unbind(e, i),
        t.isEmpty &&
          this.eventElements.splice(this.eventElements.indexOf(t), 1);
    }),
    (m.prototype.unbindAll = function () {
      this.eventElements.forEach(function (t) {
        return t.unbindAll();
      }),
        (this.eventElements = []);
    }),
    (m.prototype.once = function (t, e, i) {
      var l = this.eventElement(t),
        r = function (t) {
          l.unbind(e, r), i(t);
        };
      l.bind(e, r);
    });
  var y = {
      isWebKit:
        "undefined" != typeof document &&
        "WebkitAppearance" in document.documentElement.style,
      supportsTouch:
        "undefined" != typeof window &&
        ("ontouchstart" in window ||
          ("maxTouchPoints" in window.navigator &&
            0 < window.navigator.maxTouchPoints) ||
          (window.DocumentTouch && document instanceof window.DocumentTouch)),
      supportsIePointer:
        "undefined" != typeof navigator && navigator.msMaxTouchPoints,
      isChrome:
        "undefined" != typeof navigator &&
        /Chrome/i.test(navigator && navigator.userAgent),
    },
    W = {
      "click-rail": function (i) {
        i.element,
          i.event.bind(i.scrollbarY, "mousedown", function (t) {
            return t.stopPropagation();
          }),
          i.event.bind(i.scrollbarYRail, "mousedown", function (t) {
            var e =
              t.pageY -
                window.pageYOffset -
                i.scrollbarYRail.getBoundingClientRect().top >
              i.scrollbarYTop
                ? 1
                : -1;
            (i.element.scrollTop += e * i.containerHeight),
              X(i),
              t.stopPropagation();
          }),
          i.event.bind(i.scrollbarX, "mousedown", function (t) {
            return t.stopPropagation();
          }),
          i.event.bind(i.scrollbarXRail, "mousedown", function (t) {
            var e =
              t.pageX -
                window.pageXOffset -
                i.scrollbarXRail.getBoundingClientRect().left >
              i.scrollbarXLeft
                ? 1
                : -1;
            (i.element.scrollLeft += e * i.containerWidth),
              X(i),
              t.stopPropagation();
          });
      },
      "drag-thumb": function (t) {
        e(t, [
          "containerWidth",
          "contentWidth",
          "pageX",
          "railXWidth",
          "scrollbarX",
          "scrollbarXWidth",
          "scrollLeft",
          "x",
          "scrollbarXRail",
        ]),
          e(t, [
            "containerHeight",
            "contentHeight",
            "pageY",
            "railYHeight",
            "scrollbarY",
            "scrollbarYHeight",
            "scrollTop",
            "y",
            "scrollbarYRail",
          ]);
      },
      keyboard: function (n) {
        var o = n.element;
        n.event.bind(n.ownerDocument, "keydown", function (t) {
          if (
            !(
              (t.isDefaultPrevented && t.isDefaultPrevented()) ||
              t.defaultPrevented
            ) &&
            (s(o, ":hover") ||
              s(n.scrollbarX, ":focus") ||
              s(n.scrollbarY, ":focus"))
          ) {
            var e = document.activeElement || n.ownerDocument.activeElement;
            if (e) {
              if ("IFRAME" === e.tagName) e = e.contentDocument.activeElement;
              else for (; e.shadowRoot; ) e = e.shadowRoot.activeElement;
              if (
                s((r = e), "input,[contenteditable]") ||
                s(r, "select,[contenteditable]") ||
                s(r, "textarea,[contenteditable]") ||
                s(r, "button,[contenteditable]")
              )
                return;
            }
            var i = 0,
              l = 0;
            switch (t.which) {
              case 37:
                i = t.metaKey
                  ? -n.contentWidth
                  : t.altKey
                  ? -n.containerWidth
                  : -30;
                break;
              case 38:
                l = t.metaKey
                  ? n.contentHeight
                  : t.altKey
                  ? n.containerHeight
                  : 30;
                break;
              case 39:
                i = t.metaKey
                  ? n.contentWidth
                  : t.altKey
                  ? n.containerWidth
                  : 30;
                break;
              case 40:
                l = t.metaKey
                  ? -n.contentHeight
                  : t.altKey
                  ? -n.containerHeight
                  : -30;
                break;
              case 32:
                l = t.shiftKey ? n.containerHeight : -n.containerHeight;
                break;
              case 33:
                l = n.containerHeight;
                break;
              case 34:
                l = -n.containerHeight;
                break;
              case 36:
                l = n.contentHeight;
                break;
              case 35:
                l = -n.contentHeight;
                break;
              default:
                return;
            }
            (n.settings.suppressScrollX && 0 !== i) ||
              (n.settings.suppressScrollY && 0 !== l) ||
              ((o.scrollTop -= l),
              (o.scrollLeft += i),
              X(n),
              (function (t, e) {
                var i = f(o.scrollTop);
                if (0 === t) {
                  if (!n.scrollbarYActive) return;
                  if (
                    (0 === i && 0 < e) ||
                    (i >= n.contentHeight - n.containerHeight && e < 0)
                  )
                    return !n.settings.wheelPropagation;
                }
                if (((i = o.scrollLeft), 0 === e)) {
                  if (!n.scrollbarXActive) return;
                  if (
                    (0 === i && t < 0) ||
                    (i >= n.contentWidth - n.containerWidth && 0 < t)
                  )
                    return !n.settings.wheelPropagation;
                }
                return 1;
              })(i, l) && t.preventDefault());
          }
          var r;
        });
      },
      wheel: function (a) {
        function t(t) {
          var e,
            i,
            l,
            r,
            n =
              ((l = (i = t).deltaX),
              (r = -1 * i.deltaY),
              (void 0 !== l && void 0 !== r) ||
                ((l = (-1 * i.wheelDeltaX) / 6), (r = i.wheelDeltaY / 6)),
              i.deltaMode && 1 === i.deltaMode && ((l *= 10), (r *= 10)),
              l != l && r != r && ((l = 0), (r = i.wheelDelta)),
              i.shiftKey ? [-r, -l] : [l, r]),
            o = n[0],
            s = n[1];
          !(function (t, e, i) {
            if (!y.isWebKit && c.querySelector("select:focus")) return 1;
            if (c.contains(t))
              for (var l = t; l && l !== c; ) {
                if (l.classList.contains(w.element.consuming)) return 1;
                var r = b(l);
                if (i && r.overflowY.match(/(scroll|auto)/)) {
                  var n = l.scrollHeight - l.clientHeight;
                  if (
                    0 < n &&
                    ((0 < l.scrollTop && i < 0) || (l.scrollTop < n && 0 < i))
                  )
                    return 1;
                }
                if (e && r.overflowX.match(/(scroll|auto)/)) {
                  r = l.scrollWidth - l.clientWidth;
                  if (
                    0 < r &&
                    ((0 < l.scrollLeft && e < 0) || (l.scrollLeft < r && 0 < e))
                  )
                    return 1;
                }
                l = l.parentNode;
              }
          })(t.target, o, s) &&
            ((e = !1),
            a.settings.useBothWheelAxes
              ? a.scrollbarYActive && !a.scrollbarXActive
                ? (s
                    ? (c.scrollTop -= s * a.settings.wheelSpeed)
                    : (c.scrollTop += o * a.settings.wheelSpeed),
                  (e = !0))
                : a.scrollbarXActive &&
                  !a.scrollbarYActive &&
                  (o
                    ? (c.scrollLeft += o * a.settings.wheelSpeed)
                    : (c.scrollLeft -= s * a.settings.wheelSpeed),
                  (e = !0))
              : ((c.scrollTop -= s * a.settings.wheelSpeed),
                (c.scrollLeft += o * a.settings.wheelSpeed)),
            X(a),
            (e =
              e ||
              ((i = o),
              (l = s),
              (r = f(c.scrollTop)),
              (n = 0 === c.scrollTop),
              (o = r + c.offsetHeight === c.scrollHeight),
              (s = 0 === c.scrollLeft),
              (r = c.scrollLeft + c.offsetWidth === c.scrollWidth),
              !(p(l) > p(i) ? n || o : s || r) ||
                !a.settings.wheelPropagation)) &&
              !t.ctrlKey &&
              (t.stopPropagation(), t.preventDefault()));
        }
        var c = a.element;
        void 0 === window.onwheel
          ? void 0 !== window.onmousewheel && a.event.bind(c, "mousewheel", t)
          : a.event.bind(c, "wheel", t);
      },
      touch: function (o) {
        function n(t, e) {
          (c.scrollTop -= e), (c.scrollLeft -= t), X(o);
        }
        function s(t) {
          return t.targetTouches ? t.targetTouches[0] : t;
        }
        function a(t) {
          return (
            (!t.pointerType || "pen" !== t.pointerType || 0 !== t.buttons) &&
            ((t.targetTouches && 1 === t.targetTouches.length) ||
              (t.pointerType &&
                "mouse" !== t.pointerType &&
                t.pointerType !== t.MSPOINTER_TYPE_MOUSE))
          );
        }
        function t(t) {
          a(t) &&
            ((t = s(t)),
            (h.pageX = t.pageX),
            (h.pageY = t.pageY),
            (u = new Date().getTime()),
            null !== l && clearInterval(l));
        }
        function e(t) {
          var e, i, l, r;
          a(t) &&
            ((e =
              (r = { pageX: (l = s(t)).pageX, pageY: l.pageY }).pageX -
              h.pageX),
            (i = r.pageY - h.pageY),
            (function (t, e, i) {
              if (c.contains(t))
                for (var l = t; l && l !== c; ) {
                  if (l.classList.contains(w.element.consuming)) return 1;
                  var r = b(l);
                  if (i && r.overflowY.match(/(scroll|auto)/)) {
                    var n = l.scrollHeight - l.clientHeight;
                    if (
                      0 < n &&
                      ((0 < l.scrollTop && i < 0) || (l.scrollTop < n && 0 < i))
                    )
                      return 1;
                  }
                  if (e && r.overflowX.match(/(scroll|auto)/)) {
                    r = l.scrollWidth - l.clientWidth;
                    if (
                      0 < r &&
                      ((0 < l.scrollLeft && e < 0) ||
                        (l.scrollLeft < r && 0 < e))
                    )
                      return 1;
                  }
                  l = l.parentNode;
                }
            })(t.target, e, i) ||
              (n(e, i),
              (h = r),
              0 < (r = (l = new Date().getTime()) - u) &&
                ((d.x = e / r), (d.y = i / r), (u = l)),
              (function (t, e) {
                var i = f(c.scrollTop),
                  l = c.scrollLeft,
                  r = p(t),
                  n = p(e);
                if (r < n) {
                  if (
                    (e < 0 && i === o.contentHeight - o.containerHeight) ||
                    (0 < e && 0 === i)
                  )
                    return 0 === window.scrollY && 0 < e && y.isChrome;
                } else if (
                  n < r &&
                  ((t < 0 && l === o.contentWidth - o.containerWidth) ||
                    (0 < t && 0 === l))
                )
                  return 1;
                return 1;
              })(e, i) && t.preventDefault()));
        }
        function i() {
          o.settings.swipeEasing &&
            (clearInterval(l),
            (l = setInterval(function () {
              return !o.isInitialized &&
                (d.x || d.y) &&
                !(p(d.x) < 0.01 && p(d.y) < 0.01) &&
                o.element
                ? (n(30 * d.x, 30 * d.y), (d.x *= 0.8), void (d.y *= 0.8))
                : void clearInterval(l);
            }, 10)));
        }
        var c, h, u, d, l;
        (y.supportsTouch || y.supportsIePointer) &&
          ((c = o.element),
          (h = {}),
          (u = 0),
          (d = {}),
          (l = null),
          y.supportsTouch
            ? (o.event.bind(c, "touchstart", t),
              o.event.bind(c, "touchmove", e),
              o.event.bind(c, "touchend", i))
            : y.supportsIePointer &&
              (window.PointerEvent
                ? (o.event.bind(c, "pointerdown", t),
                  o.event.bind(c, "pointermove", e),
                  o.event.bind(c, "pointerup", i))
                : window.MSPointerEvent &&
                  (o.event.bind(c, "MSPointerDown", t),
                  o.event.bind(c, "MSPointerMove", e),
                  o.event.bind(c, "MSPointerUp", i))));
      },
    },
    g = function (t, e) {
      var i,
        l = this;
      if (
        (void 0 === e && (e = {}),
        !(t = "string" == typeof t ? document.querySelector(t) : t) ||
          !t.nodeName)
      )
        throw new Error(
          "no element is specified to initialize PerfectScrollbar"
        );
      for (i in ((this.element = t).classList.add(w.main),
      (this.settings = {
        handlers: ["click-rail", "drag-thumb", "keyboard", "wheel", "touch"],
        maxScrollbarLength: null,
        minScrollbarLength: null,
        scrollingThreshold: 1e3,
        scrollXMarginOffset: 0,
        scrollYMarginOffset: 0,
        suppressScrollX: !1,
        suppressScrollY: !1,
        swipeEasing: !0,
        useBothWheelAxes: !1,
        wheelPropagation: !0,
        wheelSpeed: 1,
      }),
      e))
        this.settings[i] = e[i];
      (this.containerWidth = null),
        (this.containerHeight = null),
        (this.contentWidth = null),
        (this.contentHeight = null);
      var r,
        n = function () {
          return t.classList.add(w.state.focus);
        },
        o = function () {
          return t.classList.remove(w.state.focus);
        };
      (this.isRtl = "rtl" === b(t).direction),
        !0 === this.isRtl && t.classList.add(w.rtl),
        (this.isNegativeScroll =
          ((r = t.scrollLeft),
          (t.scrollLeft = -1),
          (s = t.scrollLeft < 0),
          (t.scrollLeft = r),
          s)),
        (this.negativeScrollAdjustment = this.isNegativeScroll
          ? t.scrollWidth - t.clientWidth
          : 0),
        (this.event = new m()),
        (this.ownerDocument = t.ownerDocument || document),
        (this.scrollbarXRail = c(w.element.rail("x"))),
        t.appendChild(this.scrollbarXRail),
        (this.scrollbarX = c(w.element.thumb("x"))),
        this.scrollbarXRail.appendChild(this.scrollbarX),
        this.scrollbarX.setAttribute("tabindex", 0),
        this.event.bind(this.scrollbarX, "focus", n),
        this.event.bind(this.scrollbarX, "blur", o),
        (this.scrollbarXActive = null),
        (this.scrollbarXWidth = null),
        (this.scrollbarXLeft = null);
      var s = b(this.scrollbarXRail);
      (this.scrollbarXBottom = parseInt(s.bottom, 10)),
        isNaN(this.scrollbarXBottom)
          ? ((this.isScrollbarXUsingBottom = !1),
            (this.scrollbarXTop = h(s.top)))
          : (this.isScrollbarXUsingBottom = !0),
        (this.railBorderXWidth = h(s.borderLeftWidth) + h(s.borderRightWidth)),
        a(this.scrollbarXRail, { display: "block" }),
        (this.railXMarginWidth = h(s.marginLeft) + h(s.marginRight)),
        a(this.scrollbarXRail, { display: "" }),
        (this.railXWidth = null),
        (this.railXRatio = null),
        (this.scrollbarYRail = c(w.element.rail("y"))),
        t.appendChild(this.scrollbarYRail),
        (this.scrollbarY = c(w.element.thumb("y"))),
        this.scrollbarYRail.appendChild(this.scrollbarY),
        this.scrollbarY.setAttribute("tabindex", 0),
        this.event.bind(this.scrollbarY, "focus", n),
        this.event.bind(this.scrollbarY, "blur", o),
        (this.scrollbarYActive = null),
        (this.scrollbarYHeight = null),
        (this.scrollbarYTop = null);
      n = b(this.scrollbarYRail);
      (this.scrollbarYRight = parseInt(n.right, 10)),
        isNaN(this.scrollbarYRight)
          ? ((this.isScrollbarYUsingRight = !1),
            (this.scrollbarYLeft = h(n.left)))
          : (this.isScrollbarYUsingRight = !0),
        (this.scrollbarYOuterWidth = this.isRtl
          ? h((o = b((o = this.scrollbarY))).width) +
            h(o.paddingLeft) +
            h(o.paddingRight) +
            h(o.borderLeftWidth) +
            h(o.borderRightWidth)
          : null),
        (this.railBorderYWidth = h(n.borderTopWidth) + h(n.borderBottomWidth)),
        a(this.scrollbarYRail, { display: "block" }),
        (this.railYMarginHeight = h(n.marginTop) + h(n.marginBottom)),
        a(this.scrollbarYRail, { display: "" }),
        (this.railYHeight = null),
        (this.railYRatio = null),
        (this.reach = {
          x:
            t.scrollLeft <= 0
              ? "start"
              : t.scrollLeft >= this.contentWidth - this.containerWidth
              ? "end"
              : null,
          y:
            t.scrollTop <= 0
              ? "start"
              : t.scrollTop >= this.contentHeight - this.containerHeight
              ? "end"
              : null,
        }),
        (this.isAlive = !0),
        this.settings.handlers.forEach(function (t) {
          return W[t](l);
        }),
        (this.lastScrollTop = f(t.scrollTop)),
        (this.lastScrollLeft = t.scrollLeft),
        this.event.bind(this.element, "scroll", function (t) {
          return l.onScroll(t);
        }),
        X(this);
    };
  return (
    (g.prototype.update = function () {
      this.isAlive &&
        ((this.negativeScrollAdjustment = this.isNegativeScroll
          ? this.element.scrollWidth - this.element.clientWidth
          : 0),
        a(this.scrollbarXRail, { display: "block" }),
        a(this.scrollbarYRail, { display: "block" }),
        (this.railXMarginWidth =
          h(b(this.scrollbarXRail).marginLeft) +
          h(b(this.scrollbarXRail).marginRight)),
        (this.railYMarginHeight =
          h(b(this.scrollbarYRail).marginTop) +
          h(b(this.scrollbarYRail).marginBottom)),
        a(this.scrollbarXRail, { display: "none" }),
        a(this.scrollbarYRail, { display: "none" }),
        X(this),
        t(this, "top", 0, !1, !0),
        t(this, "left", 0, !1, !0),
        a(this.scrollbarXRail, { display: "" }),
        a(this.scrollbarYRail, { display: "" }));
    }),
    (g.prototype.onScroll = function () {
      this.isAlive &&
        (X(this),
        t(this, "top", this.element.scrollTop - this.lastScrollTop),
        t(this, "left", this.element.scrollLeft - this.lastScrollLeft),
        (this.lastScrollTop = f(this.element.scrollTop)),
        (this.lastScrollLeft = this.element.scrollLeft));
    }),
    (g.prototype.destroy = function () {
      this.isAlive &&
        (this.event.unbindAll(),
        n(this.scrollbarX),
        n(this.scrollbarY),
        n(this.scrollbarXRail),
        n(this.scrollbarYRail),
        this.removePsClasses(),
        (this.element = null),
        (this.scrollbarX = null),
        (this.scrollbarY = null),
        (this.scrollbarXRail = null),
        (this.scrollbarYRail = null),
        (this.isAlive = !1));
    }),
    (g.prototype.removePsClasses = function () {
      this.element.className = this.element.className
        .split(" ")
        .filter(function (t) {
          return !t.match(/^ps([-_].+|)$/);
        })
        .join(" ");
    }),
    g
  );
});
const sidebarnav = document.getElementById("sidebarnav");
sidebarnav &&
  sidebarnav.addEventListener("click", (e) => {
    let i = e.target.closest("a");
    if (i) {
      var s = i.getAttribute("href");
      if (
        (("#" != s &&
          "" != s &&
          "/#" != s &&
          "#/" != s &&
          "javascript:void(0)" != s) ||
          e.preventDefault(),
        i.classList.contains("active"))
      ) {
        if (i.classList.contains("active")) {
          let e = i.nextElementSibling;
          (e = e || i.closest("ul")),
            e && e.classList.remove("in"),
            i.classList.remove("active");
        }
      } else {
        let e = Array.from(sidebarnav.querySelectorAll("ul")),
          s = Array.from(sidebarnav.querySelectorAll("a")),
          t = null,
          a = i.nextElementSibling;
        (a = a || i.closest("ul")),
          a && (t = a.previousElementSibling),
          e.forEach((e) => {
            e.classList.remove("in");
          }),
          s.forEach((e) => {
            e.classList.remove("active");
          }),
          a && a.classList.add("in"),
          t && t.classList.add("active"),
          i.classList.add("active");
      }
    }
  });
const elmBody = document.querySelector("body"),
  fadeOutEffect = (e) => {
    var t;
    e &&
      (t = setInterval(function () {
        e.style.opacity || (e.style.opacity = 1),
          0 < e.style.opacity
            ? (e.style.opacity -= 0.1)
            : ((e.style.display = "none"), clearInterval(t));
      }, 100));
  },
  setResize = () => {
    0 < window.innerWidth ? window.innerWidth : this.screen.width;
    const e = document.querySelector("body");
    document.querySelector(".navbar-brand span"),
      document.querySelector(".sidebartoggler i");
    const t = document.querySelector(".page-wrapper");
    e.classList.add("mini-sidebar");
    var s =
      (0 < window.innerHeight ? window.innerHeight : this.screen.height) - 1;
    55 < (s = (s -= 55) < 1 ? 1 : s) && t && (t.style.minHeight = s + "px");
  };
window.addEventListener("DOMContentLoaded", (e) => {
  setResize();
  var t = document.querySelector(".scroll-sidebar");
  t && new PerfectScrollbar(t);
}),
  window.addEventListener("load", (e) => {
    var t = document.querySelector(".preloader");
    fadeOutEffect(t);
  }),
  window.addEventListener("resize", setResize),
  elmBody.addEventListener("resize", setResize);
const elmSidebartoggler = document.querySelector(".sidebartoggler");
elmSidebartoggler &&
  elmSidebartoggler.addEventListener("click", (e) => {
    const t = document.querySelector(".navbar-brand span");
    elmBody.classList.contains("mini-sidebar")
      ? (setResize(),
        elmBody.classList.remove("mini-sidebar"),
        t && (t.style.display = "block"))
      : (setResize(),
        elmBody.classList.add("mini-sidebar"),
        t && (t.style.display = "none"));
  });
const elmNavToggler = document.querySelector(".nav-toggler");
elmNavToggler &&
  elmNavToggler.addEventListener("click", (e) => {
    const t = document.querySelector(".nav-toggler i");
    elmBody.classList.toggle("show-sidebar"),
      t && (t.classList.toggle("fa-bars"), t.classList.toggle("fa-times"));
  });
