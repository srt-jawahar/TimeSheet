(this["webpackJsonp@minimal/minimal-kit-react"]=this["webpackJsonp@minimal/minimal-kit-react"]||[]).push([[46],{781:function(e,t,n){"use strict";n.d(t,"a",(function(){return d}));var a=n(2),i=n(36),c=n(206),r=n(312),s=n(257),l=n(752),j=n(34),b=n(0),u=["links","action","heading","moreLink","sx"];function d(e){var t=e.links,n=e.action,d=e.heading,h=e.moreLink,O=void 0===h?[]:h,o=e.sx,g=Object(i.a)(e,u);return Object(b.jsxs)(r.a,{sx:Object(a.a)({mb:5},o),children:[Object(b.jsxs)(r.a,{sx:{display:"flex",alignItems:"center"},children:[Object(b.jsxs)(r.a,{sx:{flexGrow:1},children:[Object(b.jsx)(s.a,{variant:"h4",gutterBottom:!0,children:d}),Object(b.jsx)(j.a,Object(a.a)({links:t},g))]}),n&&Object(b.jsx)(r.a,{sx:{flexShrink:0},children:n})]}),Object(b.jsx)(r.a,{sx:{mt:2},children:Object(c.isString)(O)?Object(b.jsx)(l.a,{href:O,target:"_blank",variant:"body2",children:O}):O.map((function(e){return Object(b.jsx)(l.a,{noWrap:!0,href:e,variant:"body2",target:"_blank",sx:{display:"table"},children:e},e)}))})]})}},908:function(e,t,n){"use strict";n.r(t),n.d(t,"default",(function(){return _}));var a=n(25),i=n(2),c=n(16),r=n(1),s=n.n(r),l=n(764),j=n(773),b=n(312),u=n(724),d=n(765),h=n(821),O=n(786),o=n(805),g=n(806),x=n(807),m=n(808),f=n(848),p=n(809),v=n(767),y=n(741),k=n(775),S=n(834),R=n(849),I=n(443),w=n(749),D=n(438),M=n(924),P=n(76),E=n(24),T=n(83),A=n.n(T),C=n(46),N=n(31),K=n(781),B=n(152),z=n(113),U=n(154),V=n(322),W=n(34),J=n(0),L=[{id:"kpi-name",label:"KPI",alignRight:!1},{id:"kpi-description",label:"Description",alignRight:!1},{id:"kpi-rating",label:"Rating",alignRight:!1},{id:"selfrating",label:"Self Rating",alignRight:!1},{id:"managerRating",label:"Manager Rating",alignRight:!1}];function _(){var e=Object(B.a)().themeStretch,t="KPI-KRA Self Rating",n=Object(P.b)(),r=n.enqueueSnackbar,T=n.closeSnackbar,_=Object(C.b)(),q=s.a.useState(new Date),F=Object(c.a)(q,2),G=F[0],Y=F[1],H=Object(C.c)(V.f),Q=Object(C.c)(V.e),X=Object(C.c)(V.c),Z=0===H.length?0:H.map((function(e){return e.rating})).reduce((function(e,t){return Number(e)+Number(t)})),$=0===H.length?0:H.map((function(e){return e.selfrating})).reduce((function(e,t){return Number(e)+Number(t)})),ee=0===H.length?0:H.map((function(e){return e.managerRating})).reduce((function(e,t){return Number(e)+Number(t)})),te=function(e){var t={state:e,formatteDate:Object(D.a)(G,"MM/yyyy")};_(Object(V.m)(t)),r("save"===e?"Saved successfully":"Submitted successfully",{variant:"success",action:function(e){return Object(J.jsx)(W.c,{size:"small",onClick:function(){return T(e)},children:Object(J.jsx)(E.a,{icon:A.a})})}})};return s.a.useEffect((function(){var e=Object(D.a)(G,"MM/yyyy");_(Object(V.d)(e))}),[G,_]),Object(J.jsx)(z.a,{title:t,children:Object(J.jsxs)(l.a,{maxWidth:!e&&"lg",children:[Object(J.jsx)(K.a,{heading:t,links:[{name:"Dashboard",href:N.b.general.root},{name:"KPI-KRA Self Rating"}]}),Object(J.jsxs)(j.a,{children:[Object(J.jsx)(b.a,{m:2,children:Object(J.jsxs)(u.a,{direction:"row",alignItems:"center",justifyContent:"space-between",children:[Object(J.jsx)(M.a,{views:["month","year"],label:"Month and Year",value:G,onChange:function(e){return function(e){Y(e);var t=Object(D.a)(e,"MM/yyyy");_(Object(V.d)(t))}(e)},renderInput:function(e){return Object(J.jsx)(d.a,Object(i.a)({},e))}}),X?null:Object(J.jsx)(h.a,{label:Q.status,color:"SUBMITTED"===Q.status||"APPROVED"===Q.status?"success":"primary"})]})}),Object(J.jsx)(O.a,{children:Object(J.jsx)(U.a,{children:Object(J.jsx)(o.a,{sx:{minWidth:800},children:Object(J.jsxs)(g.a,{children:[Object(J.jsx)(x.a,{children:Object(J.jsx)(m.a,{style:{whiteSpace:"nowrap"},children:L.map((function(e){return Object(J.jsx)(f.a,{align:e.alignRight?"right":"left",children:e.label},e.id)}))})}),Object(J.jsx)(p.a,{children:H.map((function(e,t){var n=e.kpi,i=e.description,c=e.rating,r=e.selfrating,s=e.managerRating;return Object(J.jsxs)(m.a,{hover:!0,tabIndex:-1,children:[Object(J.jsx)(f.a,{align:"left",children:n}),Object(J.jsx)(f.a,{align:"left",children:i}),Object(J.jsx)(f.a,{align:"right",children:c}),Object(J.jsx)(f.a,{align:"right",children:"SUBMITTED"===Q.status||"APPROVED"===Q.status?Object(J.jsx)(J.Fragment,{children:r}):Object(J.jsx)(v.a,{fullWidth:!0,children:Object(J.jsxs)(y.a,{displayEmpty:!0,size:"small",value:r,onChange:function(t){return function(e,t){var n={row:e,value:t.target.value};_(Object(V.k)(n))}(e,t)},children:[Object(J.jsx)(k.a,{value:"0",children:Object(J.jsx)("em",{children:"None"})}),Object(a.a)(Array.from({length:+c},(function(e,t){return t+1}))).map((function(e,t){return Object(J.jsx)(k.a,{value:e,children:e},t)}))]})})}),Object(J.jsx)(f.a,{align:"right",children:s||"-"})]},t)}))}),Object(J.jsxs)(S.a,{children:[Object(J.jsxs)(m.a,{children:[Object(J.jsx)(f.a,{colSpan:1}),Object(J.jsx)(f.a,{align:"center",children:"Total "}),Object(J.jsx)(f.a,{align:"right",children:Z}),Object(J.jsx)(f.a,{align:"right",children:$}),Object(J.jsx)(f.a,{align:"right",children:ee})]}),Object(J.jsxs)(m.a,{children:[Object(J.jsx)(f.a,{colSpan:1}),Object(J.jsx)(f.a,{align:"center",children:"KPI - (100/10) "}),Object(J.jsx)(f.a,{align:"right",children:Z/10}),Object(J.jsx)(f.a,{align:"right",children:$/10}),Object(J.jsx)(f.a,{align:"right",children:ee/10})]})]})]})})})}),Object(J.jsx)(R.a,{disableSpacing:!0,children:Object(J.jsxs)(u.a,{style:{width:"100em"},direction:"row",spacing:2,alignItems:"center",justifyContent:"flex-end",component:I.a,children:[Object(J.jsx)(w.a,{size:"small",variant:"contained",onClick:function(){return te("save")},disabled:"SUBMITTED"===Q.status||"APPROVED"===Q.status||X,children:"Save"}),Object(J.jsx)(w.a,{size:"small",variant:"contained",onClick:function(){return te("submit")},disabled:"SUBMITTED"===Q.status||"APPROVED"===Q.status||X,children:"Submit"})]})})]})]})})}}}]);
//# sourceMappingURL=46.4b619f75.chunk.js.map