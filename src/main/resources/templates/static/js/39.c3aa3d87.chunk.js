(this["webpackJsonp@minimal/minimal-kit-react"]=this["webpackJsonp@minimal/minimal-kit-react"]||[]).push([[39],{781:function(e,t,n){"use strict";n.d(t,"a",(function(){return u}));var a=n(2),r=n(36),i=n(206),c=n(312),s=n(257),o=n(752),l=n(34),b=n(0),j=["links","action","heading","moreLink","sx"];function u(e){var t=e.links,n=e.action,u=e.heading,d=e.moreLink,m=void 0===d?[]:d,h=e.sx,O=Object(r.a)(e,j);return Object(b.jsxs)(c.a,{sx:Object(a.a)({mb:5},h),children:[Object(b.jsxs)(c.a,{sx:{display:"flex",alignItems:"center"},children:[Object(b.jsxs)(c.a,{sx:{flexGrow:1},children:[Object(b.jsx)(s.a,{variant:"h4",gutterBottom:!0,children:u}),Object(b.jsx)(l.a,Object(a.a)({links:t},O))]}),n&&Object(b.jsx)(c.a,{sx:{flexShrink:0},children:n})]}),Object(b.jsx)(c.a,{sx:{mt:2},children:Object(i.isString)(m)?Object(b.jsx)(o.a,{href:m,target:"_blank",variant:"body2",children:m}):m.map((function(e){return Object(b.jsx)(o.a,{noWrap:!0,href:e,variant:"body2",target:"_blank",sx:{display:"table"},children:e},e)}))})]})}},786:function(e,t,n){"use strict";var a=n(3),r=n(6),i=n(1),c=(n(10),n(11)),s=n(151),o=n(7),l=n(13),b=n(101),j=n(112);function u(e){return Object(b.a)("MuiCardContent",e)}Object(j.a)("MuiCardContent",["root"]);var d=n(0),m=["className","component"],h=Object(o.a)("div",{name:"MuiCardContent",slot:"Root",overridesResolver:function(e,t){return t.root}})((function(){return{padding:16,"&:last-child":{paddingBottom:24}}})),O=i.forwardRef((function(e,t){var n=Object(l.a)({props:e,name:"MuiCardContent"}),i=n.className,o=n.component,b=void 0===o?"div":o,j=Object(r.a)(n,m),O=Object(a.a)({},n,{component:b}),x=function(e){var t=e.classes;return Object(s.a)({root:["root"]},u,t)}(O);return Object(d.jsx)(h,Object(a.a)({as:b,className:Object(c.a)(x.root,i),ownerState:O,ref:t},j))}));t.a=O},912:function(e,t,n){"use strict";n.r(t),n.d(t,"default",(function(){return S}));var a=n(2),r=n(8),i=n.n(r),c=n(18),s=(n(1),n(764)),o=n(724),l=n(765),b=n(773),j=n(786),u=n(47),d=n(76),m=n(46),h=n(58),O=n(780),x=n(31),p=n(781),f=n(152),g=n(113),v=n(260),k=n(0);function S(){var e=Object(f.a)().themeStretch,t="Project Master",n=Object(d.b)().enqueueSnackbar,r=Object(m.b)(),S=Object(m.c)(v.i),C=u.c().shape({name:u.e().required("KPI Name is required"),description:u.e(),rating:u.b().required("Rating is required").max(100,"Maximum number should be 100").min(1,"Minimum nuber should be 1")}),w=Object(h.d)({initialValues:{name:"",description:"",rating:""},validationSchema:C,onSubmit:function(){var e=Object(c.a)(i.a.mark((function e(t,a){var c,s;return i.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:c=a.setSubmitting,s=a.resetForm;try{r(Object(v.b)(t)),s(),c(!1),n("Created successfully",{variant:"success"})}catch(i){console.error(i),c(!1)}case 2:case"end":return e.stop()}}),e)})));return function(t,n){return e.apply(this,arguments)}}()}),y=w.errors,M=w.touched,R=w.handleSubmit,W=w.isSubmitting,q=w.getFieldProps;return Object(k.jsx)(g.a,{title:t,children:Object(k.jsxs)(s.a,{maxWidth:!e&&"lg",children:[Object(k.jsx)(p.a,{heading:t,links:[{name:"Dashboard",href:x.b.general.root},{name:"Settings",href:x.b.timesheet.settings},{name:"Project Master",href:x.b.timesheet.projectMaster},{name:"Create"}]}),Object(k.jsx)(b.a,{children:Object(k.jsx)(j.a,{children:Object(k.jsx)(h.c,{value:w,children:Object(k.jsx)(h.b,{noValidate:!0,autoComplete:"off",onSubmit:R,children:Object(k.jsxs)(o.a,{spacing:3,children:[Object(k.jsx)(l.a,Object(a.a)(Object(a.a)({fullWidth:!0,label:"KPI"},q("name")),{},{error:Boolean(M.name&&y.name),helperText:M.name&&y.name})),Object(k.jsx)(l.a,Object(a.a)(Object(a.a)({fullWidth:!0,multiline:!0,minRows:3,maxRows:5,label:"Description"},q("description")),{},{error:Boolean(M.description&&y.description),helperText:M.description&&y.description})),Object(k.jsx)(l.a,Object(a.a)(Object(a.a)({fullWidth:!0,label:"Rating",type:"number"},q("rating")),{},{error:Boolean(M.rating&&y.rating),helperText:M.rating&&y.rating})),Object(k.jsx)(O.a,{fullWidth:!0,type:"submit",variant:"contained",size:"large",loading:W||S,children:"Save"})]})})})})})]})})}}}]);
//# sourceMappingURL=39.c3aa3d87.chunk.js.map