package com.lti.upskill.userservice.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userId;

        @Column(nullable = false, unique = true, length = 40)
        private String email;

        @Column(nullable = false, length = 60)
        private String password;

        @Column(name = "first_name", nullable = false, length = 20)
        private String firstName;

        @Column(name = "last_name", nullable = false, length = 20)
        private String lastName;

        @Column(name="user_name", length = 20)
        private String username;

        @Column(name = "Role", nullable = false, length = 10)
        private  String role;

        private Long courseId;

//        private boolean enabled = false;
//        private @NotBlank boolean loggedIn;

//        public boolean isLoggedIn() {
//                return loggedIn;
//        }
//
//        public void setLoggedIn(boolean loggedIn) {
//                this.loggedIn = loggedIn;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//                if (this == o) return true;
//                if (!(o instanceof User)) return false;
//                User user = (User) o;
//                return isEnabled() == user.isEnabled() && isLoggedIn() == user.isLoggedIn()
//                        && Objects.equals(getId(), user.getId())
//                        && Objects.equals(getPassword(), user.getPassword()) ;
//        }
//
//        @Override
//        public int hashCode() {
//                return Objects.hash(getId(), getUsername(),getPassword(), isEnabled(), isLoggedIn());
//        }
}
