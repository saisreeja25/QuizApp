
	import java.util.*;

	class Player {
	    String name, email, phone;
	    int cashPrize;
	    boolean[] lifelines; 

	    Player(String name, String email, String phone) {
	        this.name = name;
	        this.email = email;
	        this.phone = phone;
	        this.cashPrize = 0;
	        this.lifelines = new boolean[]{true, true, true};
	    }
	}


