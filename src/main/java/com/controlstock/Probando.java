package com.controlstock;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;

public class Probando {

    //
    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword; //Entidad ForgotPassword

    @Enumerated(EnumType.STRING)
    private UserRole role;  //Enum UserRole  (USER - ADMIN)

    private boolean isEnabled = true;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    //

    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword; //Entidad ForgotPassword

    @Enumerated(EnumType.STRING)
    private UserRole role;  //Enum UserRole  (USER - ADMIN)

    private boolean isEnabled = true;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;



}
