package com.schoolproject.airbnbclone.models;

import com.schoolproject.airbnbclone.dtos.user.response.UserBasicDetails;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@NamedNativeQuery(
        name = "userQry",
        query = "SELECT u.username AS username, u.first_name AS firstName, u.last_name AS lastName, u.phone_number AS phoneNumber, i.path AS image " +
                "FROM User u " +
                "INNER JOIN users_roles ur " +
                "ON u.id = ur.user_id " +
                "INNER JOIN image i " +
                "ON u.id = i.user_id " +
                "WHERE ur.role_id = 3 " +
                "AND u.host_approved = false",
        resultSetMapping = "UserBasicResult"
)
@SqlResultSetMapping(name = "UserBasicResult",
        classes = {
                @ConstructorResult(
                        targetClass = UserBasicDetails.class,
                        columns = {
                                @ColumnResult(name = "username"),
                                @ColumnResult(name = "firstName"),
                                @ColumnResult(name = "lastName"),
                                @ColumnResult(name = "phoneNumber"),
                                @ColumnResult(name = "image")
                        }
                )
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Image image;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    @Column(nullable = false)
    private boolean hostApproved;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = this.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
