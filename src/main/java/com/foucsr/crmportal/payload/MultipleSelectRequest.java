package com.foucsr.crmportal.payload;


    public class MultipleSelectRequest {


        public Long[] ids;

        public String leadOwner;

        private char is_Active;


         public char getIs_Active() {
	         return is_Active;
         }

         public void setIs_Active(char is_Active) {
	          this.is_Active = is_Active;
         }

         public String getLeadOwner() {
              return leadOwner;
         }

         public void setLeadOwner(String leadOwner) {
              this.leadOwner = leadOwner;
         }

         public Long[] getIds() {
              return ids;
         }

         public void setIds(Long[] ids) {
              this.ids = ids;
         }

	
}

