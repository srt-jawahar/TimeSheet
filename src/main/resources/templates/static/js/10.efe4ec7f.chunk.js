(this["webpackJsonp@minimal/minimal-kit-react"]=this["webpackJsonp@minimal/minimal-kit-react"]||[]).push([[10],{781:function(e,t,a){"use strict";a.d(t,"a",(function(){return j}));var n=a(2),r=a(36),c=a(206),o=a(312),i=a(257),l=a(752),s=a(34),d=a(0),b=["links","action","heading","moreLink","sx"];function j(e){var t=e.links,a=e.action,j=e.heading,u=e.moreLink,h=void 0===u?[]:u,O=e.sx,f=Object(r.a)(e,b);return Object(d.jsxs)(o.a,{sx:Object(n.a)({mb:5},O),children:[Object(d.jsxs)(o.a,{sx:{display:"flex",alignItems:"center"},children:[Object(d.jsxs)(o.a,{sx:{flexGrow:1},children:[Object(d.jsx)(i.a,{variant:"h4",gutterBottom:!0,children:j}),Object(d.jsx)(s.a,Object(n.a)({links:t},f))]}),a&&Object(d.jsx)(o.a,{sx:{flexShrink:0},children:a})]}),Object(d.jsx)(o.a,{sx:{mt:2},children:Object(c.isString)(h)?Object(d.jsx)(l.a,{href:h,target:"_blank",variant:"body2",children:h}):h.map((function(e){return Object(d.jsx)(l.a,{noWrap:!0,href:e,variant:"body2",target:"_blank",sx:{display:"table"},children:e},e)}))})]})}},790:function(e,t){t.__esModule=!0,t.default={body:'<path fill="currentColor" d="M19 11h-6V5a1 1 0 0 0-2 0v6H5a1 1 0 0 0 0 2h6v6a1 1 0 0 0 2 0v-6h6a1 1 0 0 0 0-2z"/>',width:24,height:24}},800:function(e,t){t.__esModule=!0,t.default={body:'<path fill="currentColor" d="M21 6h-5V4.33A2.42 2.42 0 0 0 13.5 2h-3A2.42 2.42 0 0 0 8 4.33V6H3a1 1 0 0 0 0 2h1v11a3 3 0 0 0 3 3h10a3 3 0 0 0 3-3V8h1a1 1 0 0 0 0-2zM10 16a1 1 0 0 1-2 0v-4a1 1 0 0 1 2 0zm0-11.67c0-.16.21-.33.5-.33h3c.29 0 .5.17.5.33V6h-4zM16 16a1 1 0 0 1-2 0v-4a1 1 0 0 1 2 0z"/>',width:24,height:24}},805:function(e,t,a){"use strict";var n=a(3),r=a(6),c=a(1),o=(a(10),a(11)),i=a(151),l=a(13),s=a(7),d=a(101),b=a(112);function j(e){return Object(d.a)("MuiTableContainer",e)}Object(b.a)("MuiTableContainer",["root"]);var u=a(0),h=["className","component"],O=Object(s.a)("div",{name:"MuiTableContainer",slot:"Root",overridesResolver:function(e,t){return t.root}})({width:"100%",overflowX:"auto"}),f=c.forwardRef((function(e,t){var a=Object(l.a)({props:e,name:"MuiTableContainer"}),c=a.className,s=a.component,d=void 0===s?"div":s,b=Object(r.a)(a,h),f=Object(n.a)({},a,{component:d}),p=function(e){var t=e.classes;return Object(i.a)({root:["root"]},j,t)}(f);return Object(u.jsx)(O,Object(n.a)({ref:t,as:d,className:Object(o.a)(p.root,c),ownerState:f},b))}));t.a=f},806:function(e,t,a){"use strict";var n=a(6),r=a(3),c=a(1),o=(a(10),a(11)),i=a(151),l=a(794),s=a(13),d=a(7),b=a(101),j=a(112);function u(e){return Object(b.a)("MuiTable",e)}Object(j.a)("MuiTable",["root","stickyHeader"]);var h=a(0),O=["className","component","padding","size","stickyHeader"],f=Object(d.a)("table",{name:"MuiTable",slot:"Root",overridesResolver:function(e,t){var a=e.ownerState;return[t.root,a.stickyHeader&&t.stickyHeader]}})((function(e){var t=e.theme,a=e.ownerState;return Object(r.a)({display:"table",width:"100%",borderCollapse:"collapse",borderSpacing:0,"& caption":Object(r.a)({},t.typography.body2,{padding:t.spacing(2),color:t.palette.text.secondary,textAlign:"left",captionSide:"bottom"})},a.stickyHeader&&{borderCollapse:"separate"})})),p="table",m=c.forwardRef((function(e,t){var a=Object(s.a)({props:e,name:"MuiTable"}),d=a.className,b=a.component,j=void 0===b?p:b,m=a.padding,v=void 0===m?"normal":m,x=a.size,g=void 0===x?"medium":x,y=a.stickyHeader,w=void 0!==y&&y,S=Object(n.a)(a,O),M=Object(r.a)({},a,{component:j,padding:v,size:g,stickyHeader:w}),k=function(e){var t=e.classes,a={root:["root",e.stickyHeader&&"stickyHeader"]};return Object(i.a)(a,u,t)}(M),C=c.useMemo((function(){return{padding:v,size:g,stickyHeader:w}}),[v,g,w]);return Object(h.jsx)(l.a.Provider,{value:C,children:Object(h.jsx)(f,Object(r.a)({as:j,role:j===p?null:"table",ref:t,className:Object(o.a)(k.root,d),ownerState:M},S))})}));t.a=m},807:function(e,t,a){"use strict";var n=a(3),r=a(6),c=a(1),o=(a(10),a(11)),i=a(151),l=a(782),s=a(13),d=a(7),b=a(101),j=a(112);function u(e){return Object(b.a)("MuiTableHead",e)}Object(j.a)("MuiTableHead",["root"]);var h=a(0),O=["className","component"],f=Object(d.a)("thead",{name:"MuiTableHead",slot:"Root",overridesResolver:function(e,t){return t.root}})({display:"table-header-group"}),p={variant:"head"},m="thead",v=c.forwardRef((function(e,t){var a=Object(s.a)({props:e,name:"MuiTableHead"}),c=a.className,d=a.component,b=void 0===d?m:d,j=Object(r.a)(a,O),v=Object(n.a)({},a,{component:b}),x=function(e){var t=e.classes;return Object(i.a)({root:["root"]},u,t)}(v);return Object(h.jsx)(l.a.Provider,{value:p,children:Object(h.jsx)(f,Object(n.a)({as:b,className:Object(o.a)(x.root,c),ref:t,role:b===m?null:"rowgroup",ownerState:v},j))})}));t.a=v},808:function(e,t,a){"use strict";var n=a(4),r=a(3),c=a(6),o=a(1),i=(a(10),a(11)),l=a(151),s=a(64),d=a(782),b=a(13),j=a(7),u=a(101),h=a(112);function O(e){return Object(u.a)("MuiTableRow",e)}var f=Object(h.a)("MuiTableRow",["root","selected","hover","head","footer"]),p=a(0),m=["className","component","hover","selected"],v=Object(j.a)("tr",{name:"MuiTableRow",slot:"Root",overridesResolver:function(e,t){var a=e.ownerState;return[t.root,a.head&&t.head,a.footer&&t.footer]}})((function(e){var t,a=e.theme;return t={color:"inherit",display:"table-row",verticalAlign:"middle",outline:0},Object(n.a)(t,"&.".concat(f.hover,":hover"),{backgroundColor:a.palette.action.hover}),Object(n.a)(t,"&.".concat(f.selected),{backgroundColor:Object(s.a)(a.palette.primary.main,a.palette.action.selectedOpacity),"&:hover":{backgroundColor:Object(s.a)(a.palette.primary.main,a.palette.action.selectedOpacity+a.palette.action.hoverOpacity)}}),t})),x=o.forwardRef((function(e,t){var a=Object(b.a)({props:e,name:"MuiTableRow"}),n=a.className,s=a.component,j=void 0===s?"tr":s,u=a.hover,h=void 0!==u&&u,f=a.selected,x=void 0!==f&&f,g=Object(c.a)(a,m),y=o.useContext(d.a),w=Object(r.a)({},a,{component:j,hover:h,selected:x,head:y&&"head"===y.variant,footer:y&&"footer"===y.variant}),S=function(e){var t=e.classes,a={root:["root",e.selected&&"selected",e.hover&&"hover",e.head&&"head",e.footer&&"footer"]};return Object(l.a)(a,O,t)}(w);return Object(p.jsx)(v,Object(r.a)({as:j,ref:t,className:Object(i.a)(S.root,n),role:"tr"===j?null:"row",ownerState:w},g))}));t.a=x},809:function(e,t,a){"use strict";var n=a(3),r=a(6),c=a(1),o=(a(10),a(11)),i=a(151),l=a(782),s=a(13),d=a(7),b=a(101),j=a(112);function u(e){return Object(b.a)("MuiTableBody",e)}Object(j.a)("MuiTableBody",["root"]);var h=a(0),O=["className","component"],f=Object(d.a)("tbody",{name:"MuiTableBody",slot:"Root",overridesResolver:function(e,t){return t.root}})({display:"table-row-group"}),p={variant:"body"},m="tbody",v=c.forwardRef((function(e,t){var a=Object(s.a)({props:e,name:"MuiTableBody"}),c=a.className,d=a.component,b=void 0===d?m:d,j=Object(r.a)(a,O),v=Object(n.a)({},a,{component:b}),x=function(e){var t=e.classes;return Object(i.a)({root:["root"]},u,t)}(v);return Object(h.jsx)(l.a.Provider,{value:p,children:Object(h.jsx)(f,Object(n.a)({className:Object(o.a)(x.root,c),as:b,ref:t,role:b===m?null:"rowgroup",ownerState:v},j))})}));t.a=v},812:function(e,t,a){"use strict";a.d(t,"a",(function(){return s}));var n=a(2),r=a(36),c=a(443),o=a(257),i=a(0),l=["searchQuery"];function s(e){var t=e.searchQuery,a=void 0===t?"":t,s=Object(r.a)(e,l);return Object(i.jsxs)(c.a,Object(n.a)(Object(n.a)({},s),{},{children:[Object(i.jsx)(o.a,{gutterBottom:!0,align:"center",variant:"subtitle1",children:"Not found"}),Object(i.jsxs)(o.a,{variant:"body2",align:"center",children:["No results found for \xa0",Object(i.jsxs)("strong",{children:['"',a,'"']}),". Try checking for typos or using complete words."]})]}))}},813:function(e,t){t.__esModule=!0,t.default={body:'<path d="M1 21h4V9H1v12zm22-11c0-1.1-.9-2-2-2h-6.31l.95-4.57l.03-.32c0-.41-.17-.79-.44-1.06L14.17 1L7.59 7.59C7.22 7.95 7 8.45 7 9v10c0 1.1.9 2 2 2h9c.83 0 1.54-.5 1.84-1.22l3.02-7.05c.09-.23.14-.47.14-.73v-2z" fill="currentColor"/>',width:24,height:24}},824:function(e,t){t.__esModule=!0,t.default={body:'<g fill="currentColor"><path d="M12.71 11.29a1 1 0 0 0-1.4 0l-3 2.9a1 1 0 1 0 1.38 1.44L11 14.36V20a1 1 0 0 0 2 0v-5.59l1.29 1.3a1 1 0 0 0 1.42 0a1 1 0 0 0 0-1.42z"/><path d="M17.67 7A6 6 0 0 0 6.33 7a5 5 0 0 0-3.08 8.27A1 1 0 1 0 4.75 14A3 3 0 0 1 7 9h.1a1 1 0 0 0 1-.8a4 4 0 0 1 7.84 0a1 1 0 0 0 1 .8H17a3 3 0 0 1 2.25 5a1 1 0 0 0 .09 1.42a1 1 0 0 0 .66.25a1 1 0 0 0 .75-.34A5 5 0 0 0 17.67 7z"/></g>',width:24,height:24}},827:function(e,t,a){"use strict";t.a={border:0,clip:"rect(0 0 0 0)",height:"1px",margin:-1,overflow:"hidden",padding:0,position:"absolute",whiteSpace:"nowrap",width:"1px"}},833:function(e,t,a){"use strict";var n=a(4),r=a(6),c=a(3),o=a(151),i=a(11),l=(a(10),a(1)),s=a(666),d=a(42),b=a(0),j=Object(d.a)(Object(b.jsx)("path",{d:"M20 12l-1.41-1.41L13 16.17V4h-2v12.17l-5.58-5.59L4 12l8 8 8-8z"}),"ArrowDownward"),u=a(7),h=a(13),O=a(15),f=a(101),p=a(112);function m(e){return Object(f.a)("MuiTableSortLabel",e)}var v=Object(p.a)("MuiTableSortLabel",["root","active","icon","iconDirectionDesc","iconDirectionAsc"]),x=["active","children","className","direction","hideSortIcon","IconComponent"],g=Object(u.a)(s.a,{name:"MuiTableSortLabel",slot:"Root",overridesResolver:function(e,t){var a=e.ownerState;return[t.root,a.active&&t.active]}})((function(e){var t=e.theme;return Object(n.a)({cursor:"pointer",display:"inline-flex",justifyContent:"flex-start",flexDirection:"inherit",alignItems:"center","&:focus":{color:t.palette.text.secondary},"&:hover":Object(n.a)({color:t.palette.text.secondary},"& .".concat(v.icon),{opacity:.5})},"&.".concat(v.active),Object(n.a)({color:t.palette.text.primary},"& .".concat(v.icon),{opacity:1,color:t.palette.text.secondary}))})),y=Object(u.a)("span",{name:"MuiTableSortLabel",slot:"Icon",overridesResolver:function(e,t){var a=e.ownerState;return[t.icon,t["iconDirection".concat(Object(O.a)(a.direction))]]}})((function(e){var t=e.theme,a=e.ownerState;return Object(c.a)({fontSize:18,marginRight:4,marginLeft:4,opacity:0,transition:t.transitions.create(["opacity","transform"],{duration:t.transitions.duration.shorter}),userSelect:"none"},"desc"===a.direction&&{transform:"rotate(0deg)"},"asc"===a.direction&&{transform:"rotate(180deg)"})})),w=l.forwardRef((function(e,t){var a=Object(h.a)({props:e,name:"MuiTableSortLabel"}),n=a.active,l=void 0!==n&&n,s=a.children,d=a.className,u=a.direction,f=void 0===u?"asc":u,p=a.hideSortIcon,v=void 0!==p&&p,w=a.IconComponent,S=void 0===w?j:w,M=Object(r.a)(a,x),k=Object(c.a)({},a,{active:l,direction:f,hideSortIcon:v,IconComponent:S}),C=function(e){var t=e.classes,a=e.direction,n={root:["root",e.active&&"active"],icon:["icon","iconDirection".concat(Object(O.a)(a))]};return Object(o.a)(n,m,t)}(k);return Object(b.jsxs)(g,Object(c.a)({className:Object(i.a)(C.root,d),component:"span",disableRipple:!0,ownerState:k,ref:t},M,{children:[s,v&&!l?null:Object(b.jsx)(y,{as:S,className:Object(i.a)(C.icon),ownerState:k})]}))}));t.a=w},859:function(e,t){t.__esModule=!0,t.default={body:'<path d="M15 3H6c-.83 0-1.54.5-1.84 1.22l-3.02 7.05c-.09.23-.14.47-.14.73v2c0 1.1.9 2 2 2h6.31l-.95 4.57l-.03.32c0 .41.17.79.44 1.06L9.83 23l6.59-6.59c.36-.36.58-.86.58-1.41V5c0-1.1-.9-2-2-2zm4 0v12h4V3h-4z" fill="currentColor"/>',width:24,height:24}},860:function(e,t){t.__esModule=!0,t.default={body:'<path fill="currentColor" d="M19.4 7.34L16.66 4.6A2 2 0 0 0 14 4.53l-9 9a2 2 0 0 0-.57 1.21L4 18.91a1 1 0 0 0 .29.8A1 1 0 0 0 5 20h.09l4.17-.38a2 2 0 0 0 1.21-.57l9-9a1.92 1.92 0 0 0-.07-2.71zM16 10.68L13.32 8l1.95-2L18 8.73z"/>',width:24,height:24}},861:function(e,t){t.__esModule=!0,t.default={body:'<g fill="currentColor"><circle cx="12" cy="12" r="2"/><circle cx="12" cy="5" r="2"/><circle cx="12" cy="19" r="2"/></g>',width:24,height:24}},928:function(e,t,a){"use strict";a.r(t),a.d(t,"default",(function(){return fe}));var n=a(16),r=a(206),c=a(24),o=a(1),i=a(790),l=a.n(i),s=a(824),d=a.n(s),b=a(28),j=a(59),u=a(764),h=a(749),O=a(773),f=a(805),p=a(806),m=a(809),v=a(808),x=a(848),g=a(742),y=a(724),w=a(747),S=a(257),M=a(930),k=a(107),C=a(316),R=a(31),I=a(152),N=a(113),T=a(343),L=a(154),z=a(812),A=a(781),H=a(2),_=a(827),D=a(807),P=a(833),B=a(312),V=a(0);function E(e){var t=e.order,a=e.orderBy,n=e.rowCount,r=e.headLabel,c=e.numSelected,o=e.onRequestSort,i=e.onSelectAllClick;return Object(V.jsx)(D.a,{children:Object(V.jsxs)(v.a,{children:[Object(V.jsx)(x.a,{padding:"checkbox",children:Object(V.jsx)(g.a,{indeterminate:c>0&&c<n,checked:n>0&&c===n,onChange:i})}),r.map((function(e){return Object(V.jsx)(x.a,{align:e.alignRight?"right":"left",sortDirection:a===e.id&&t,children:Object(V.jsxs)(P.a,{hideSortIcon:!0,active:a===e.id,direction:a===e.id?t:"asc",onClick:(n=e.id,function(e){o(e,n)}),children:[e.label,a===e.id?Object(V.jsx)(B.a,{sx:Object(H.a)({},_.a),children:"desc"===t?"sorted descending":"sorted ascending"}):null]})},e.id);var n}))]})})}var U=a(262),W=a.n(U),F=a(800),q=a.n(F),Q=a(859),J=a.n(Q),G=a(813),X=a.n(G),Y=a(7),K=a(762),Z=a(766),$=a(771),ee=a(777),te=a(754),ae=Object(Y.a)(K.a)((function(e){return{height:96,display:"flex",justifyContent:"space-between",padding:e.theme.spacing(0,1,0,3)}})),ne=Object(Y.a)(Z.a)((function(e){var t=e.theme;return{width:240,transition:t.transitions.create(["box-shadow","width"],{easing:t.transitions.easing.easeInOut,duration:t.transitions.duration.shorter}),"&.Mui-focused":{width:320,boxShadow:t.customShadows.z8},"& fieldset":{borderWidth:"1px !important",borderColor:"".concat(t.palette.grey[50032]," !important")}}}));function re(e){var t=e.numSelected,a=e.filterName,n=e.onFilterName,r=e.onDelete,o=e.onInactive,i=e.onActive,l="light"===Object(j.a)().palette.mode;return Object(V.jsxs)(ae,{sx:Object(H.a)({},t>0&&{color:l?"primary.main":"text.primary",bgcolor:l?"primary.lighter":"primary.dark"}),children:[t>0?Object(V.jsxs)(S.a,{component:"div",variant:"subtitle1",children:[t," selected"]}):Object(V.jsx)(ne,{value:a,onChange:n,placeholder:"Search user...",startAdornment:Object(V.jsx)($.a,{position:"start",children:Object(V.jsx)(B.a,{component:c.a,icon:W.a,sx:{color:"text.disabled"}})})}),t>0&&Object(V.jsxs)(y.a,{direction:"row",spacing:2,children:[Object(V.jsx)(ee.a,{title:"Delete",children:Object(V.jsx)(te.a,{onClick:r,children:Object(V.jsx)(c.a,{icon:q.a})})})," ",Object(V.jsx)(ee.a,{title:"Inactive",children:Object(V.jsx)(te.a,{onClick:i,children:Object(V.jsx)(c.a,{icon:J.a})})})," ",Object(V.jsx)(ee.a,{title:"Active",children:Object(V.jsx)(te.a,{onClick:o,children:Object(V.jsx)(c.a,{icon:X.a})})})]})]})}var ce=a(860),oe=a.n(ce),ie=a(861),le=a.n(ie),se=a(439),de=a(775),be=a(726),je=a(727);function ue(e){var t=e.employeeId,a=Object(o.useRef)(null),r=Object(o.useState)(!1),i=Object(n.a)(r,2),l=i[0],s=i[1];return Object(V.jsxs)(V.Fragment,{children:[Object(V.jsx)(te.a,{ref:a,onClick:function(){return s(!0)},children:Object(V.jsx)(c.a,{icon:le.a,width:20,height:20})}),Object(V.jsx)(se.a,{open:l,anchorEl:a.current,onClose:function(){return s(!1)},PaperProps:{sx:{width:200,maxWidth:"100%"}},anchorOrigin:{vertical:"top",horizontal:"right"},transformOrigin:{vertical:"top",horizontal:"right"},children:Object(V.jsxs)(de.a,{component:b.b,to:"".concat(R.b.admin.userEdit,"/").concat(t),sx:{color:"text.secondary"},children:[Object(V.jsx)(be.a,{children:Object(V.jsx)(c.a,{icon:oe.a,width:24,height:24})}),Object(V.jsx)(je.a,{primary:"Edit",primaryTypographyProps:{variant:"body2"}})]})})]})}var he=[{id:"name",label:"Name",alignRight:!1},{id:"employeeId",label:"Employee Id",alignRight:!1},{id:"email",label:"Email",alignRight:!1},{id:"role",label:"Role",alignRight:!1},{id:"managerId",label:"Manager Id",alignRight:!1},{id:"is_active",label:"Status",alignRight:!1},{id:""}];function Oe(e,t,a){return t[a]<e[a]?-1:t[a]>e[a]?1:0}function fe(){var e=Object(I.a)().themeStretch,t=Object(j.a)(),a=Object(k.c)(),i=Object(k.d)((function(e){return e.user})).users,s=Object(o.useState)(0),H=Object(n.a)(s,2),_=H[0],D=H[1],P=Object(o.useState)("asc"),B=Object(n.a)(P,2),U=B[0],W=B[1],F=Object(o.useState)([]),q=Object(n.a)(F,2),Q=q[0],J=q[1],G=Object(o.useState)("name"),X=Object(n.a)(G,2),Y=X[0],K=X[1],Z=Object(o.useState)(""),$=Object(n.a)(Z,2),ee=$[0],te=$[1],ae=Object(o.useState)(5),ne=Object(n.a)(ae,2),ce=ne[0],oe=ne[1];Object(o.useEffect)((function(){a(Object(C.f)()),a(Object(C.e)())}),[a]);var ie=_>0?Math.max(0,(1+_)*ce-i.length):0,le=function(e,t,a){var n=e.map((function(e,t){return[e,t]}));return n.sort((function(e,a){var n=t(e[0],a[0]);return 0!==n?n:e[1]-a[1]})),a?Object(r.filter)(e,(function(e){return-1!==e.name.toLowerCase().indexOf(a.toLowerCase())})):n.map((function(e){return e[0]}))}(i,function(e,t){return"desc"===e?function(e,a){return Oe(e,a,t)}:function(e,a){return-Oe(e,a,t)}}(U,Y),ee),se=0===le.length,de="User Management";return Object(V.jsx)(N.a,{title:de,children:Object(V.jsxs)(u.a,{maxWidth:!e&&"lg",children:[Object(V.jsx)(A.a,{heading:de,links:[{name:"Dashboard",href:R.b.root},{name:"User Management",href:R.b.admin.userManagement},{name:"List"}],action:Object(V.jsxs)(V.Fragment,{children:[Object(V.jsx)(h.a,{variant:"contained",component:b.b,to:R.b.admin.userCreate,startIcon:Object(V.jsx)(c.a,{icon:l.a}),children:"New User"})," ",Object(V.jsx)(h.a,{variant:"contained",component:b.b,to:R.b.admin.userBulkUpload,startIcon:Object(V.jsx)(c.a,{icon:d.a}),children:"Bulk Upload"})]})}),Object(V.jsxs)(O.a,{children:[Object(V.jsx)(re,{numSelected:Q.length,filterName:ee,onFilterName:function(e){D(0),te(e.target.value)},onDelete:function(){a(Object(C.p)({ids:Q})),D(0),J([])},onInactive:function(){a(Object(C.o)({ids:Q})),D(0),J([])},onActive:function(){a(Object(C.q)({ids:Q})),D(0),J([])}}),Object(V.jsx)(L.a,{children:Object(V.jsx)(f.a,{sx:{minWidth:800},children:Object(V.jsxs)(p.a,{children:[Object(V.jsx)(E,{order:U,orderBy:Y,headLabel:he,rowCount:i.length,numSelected:Q.length,onRequestSort:function(e,t){W(Y===t&&"asc"===U?"desc":"asc"),K(t)},onSelectAllClick:function(e){if(e.target.checked){var t=i.map((function(e){return e.id}));J(t)}else J([])}}),Object(V.jsxs)(m.a,{children:[le.slice(_*ce,_*ce+ce).map((function(e){var a=e.id,n=e.name,r=e.roleDescription,c=e.employeeId,o=e.avatarUrl,i=e.isActive,l=e.email,s=e.managerId,d=-1!==Q.indexOf(a);return Object(V.jsxs)(v.a,{hover:!0,tabIndex:-1,role:"checkbox",selected:d,"aria-checked":d,children:[Object(V.jsx)(x.a,{padding:"checkbox",children:Object(V.jsx)(g.a,{checked:d,onChange:function(e){return function(e,t){var a=Q.indexOf(t),n=[];-1===a?n=n.concat(Q,t):0===a?n=n.concat(Q.slice(1)):a===Q.length-1?n=n.concat(Q.slice(0,-1)):a>0&&(n=n.concat(Q.slice(0,a),Q.slice(a+1))),J(n)}(0,a)}})}),Object(V.jsx)(x.a,{component:"th",scope:"row",padding:"none",children:Object(V.jsxs)(y.a,{direction:"row",alignItems:"center",spacing:2,children:[Object(V.jsx)(w.a,{alt:n,src:o}),Object(V.jsx)(S.a,{variant:"subtitle2",noWrap:!0,children:n})]})}),Object(V.jsx)(x.a,{align:"left",children:c}),Object(V.jsx)(x.a,{align:"left",children:l}),Object(V.jsx)(x.a,{align:"left",children:r}),Object(V.jsx)(x.a,{align:"left",children:s}),Object(V.jsx)(x.a,{align:"left",children:Object(V.jsx)(T.a,{variant:"light"===t.palette.mode?"ghost":"filled",color:"N"===i?"error":"success",children:"Y"===i?"Active":"Inactive"})}),Object(V.jsx)(x.a,{align:"right",children:Object(V.jsx)(ue,{employeeId:c})})]},a)})),ie>0&&Object(V.jsx)(v.a,{style:{height:53*ie},children:Object(V.jsx)(x.a,{colSpan:6})})]}),se&&Object(V.jsx)(m.a,{children:Object(V.jsx)(v.a,{children:Object(V.jsx)(x.a,{align:"center",colSpan:6,sx:{py:3},children:Object(V.jsx)(z.a,{searchQuery:ee})})})})]})})}),Object(V.jsx)(M.a,{rowsPerPageOptions:[5,10,25],component:"div",count:i.length,rowsPerPage:ce,page:_,onPageChange:function(e,t){D(t)},onRowsPerPageChange:function(e){oe(parseInt(e.target.value,10)),D(0)}})]})]})})}}}]);
//# sourceMappingURL=10.efe4ec7f.chunk.js.map