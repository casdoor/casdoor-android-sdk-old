# Casdoor Android SDK

[Casdoor](https://casdoor.org/docs/overview) is a UI-first centralized authentication / [Single-Sign-On (SSO)](https://en.wikipedia.org/wiki/Single_sign-on) platform based on OAuth 2.0 / OIDC.

Casdoor serves both the web UI and the login requests from the application users.

## Casdoor features:

1. Front-end and back-end separate architecture, developed by Golang, Casdoor supports high concurrency, provides web-based managing UI and supports multiple languages(Chinese, English).
2. Casdoor supports Github, Google, QQ, WeChat third-party applications login, and support the extension of third-party login with plugins.
3. With [Casbin](https://casbin.org/) based authorization management, Casdoor supports ACL, RBAC, ABAC, RESTful accessing control models.
4. Phone verification code, email verification code and forget password features.
5. Accessing logs auditing and recording.
6. Alibaba Cloud, Tencent Cloud, Qiniu Cloud image CDN cloud storage.
7. Customizable register, login, and forget password pages.
8. Casdoor supports integration with existing systems using db sync method, users can transition to Casdoor smoothly.
9. Casdoor supports mainstream databases: MySQL, PostgreSQL, SQL Server etc, and support the extension of new database with plugins.

## Casdoor Online demo

Here is an online demo deployed by Casbin.

- [Casdoor official demo](https://door.casbin.com/)

Global admin login:

- Username: `admin`
- Password: `123`

## Get Started

Before using casdoor-andorid-sdk, you should make casdoor started: [Install Guide | Casdoor](https://casdoor.org/docs/basic/server-installation), then, you can quickly implement a casdoor based login page in your own app with the following methods:

### Login state related

#### `boolean CasdoorAuth.hasLoggedIn(FragmentActivity activity)`

This method could be used to judge whether the user has logged in through casdoor sdk. You would get  `true` when he(she) has logged in. Cause based on the `SharedPreference`, you could just deliver the activity which the caller belong to. Only when the user has logged in, the casdoor sdk is likely to successfullty get info of users and do requests those need authority.

To achieve "Display different content as the login state changes" effect, you could refer to the below code fragment in onCreateView method in android fragment:

```java
if (getActivity() != null) {
    FragmentManager fragmentManager = getParentFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    if (CasdoorAuth.hasLoggedIn(getActivity())) {
        UserInfoFragment userInfoFragment = new UserInfoFragment(getActivity());
        fragmentTransaction.replace(R.id.minePage, userInfoFragment);
    } else {
        UserLoginFragment userLoginFragment = new UserLoginFragment(getActivity());
        fragmentTransaction.replace(R.id.minePage, userLoginFragment);
    }
    fragmentTransaction.commit();
}
```

> UserInfoFragment is a fragment that could display some user information or entrance of some operations that needs user rights. UserLoginFragment is a fragment that provides the entrance for casdoor sdk to complete a series of login steps.

#### `void CasdoorAuth.logout(FragmentActivity activity)`

This method could be used to logout. (just as the method name says).

### User information related

To get user's basic information, you should firstly create the `CasdoorLoginActivity` provided by casdoor android sdk, after starting the activity, the casdoor would take over the app and complete the rest part of login procedure. After the user successfully login, the sdk would be available to use all kinds of method to get user's information. In the seem time, casdoor sdk also provides `SetUser` & `SetUsers` method to get users' basic public informations (private info is masked)

#### `class CasdoorLoginActivity`

All procedure of login through casdoor is based on the `CasdoorLoginActivity`, once the user successfully login, the access_token would be write in memory which would be saved unless the app data is cleared or logout forwardly even if the app is closed.

It should be noticed that before you start the `CasdoorLoginActivity`, you need to setup config by `putExtra` method through intent. To make this easier, casdoor sdk provides some public static final String in `class CasdoorConfig`. The extra Strings you need to put is:

- `Endpoint`: equal to `CasdoorConfig.ENDPOINT`, Casdoor Server Url, such as `http://localhost:8000`, `https://door.casbin.com`, etc.
- `ClientID`: equal to `CasdoorConfig.CLIENTID`,  Application's client_id in casdoor, you could find it in `Applications` - `Edit Applications` - `Client ID`. The `ClientID` should look like this: `0ba528121ea87b3eb54d`.
- `ClientSecret`: equal to `CasdoorConfig.CLIENTSECRET`, which as seems as the `ClientID`. The `ClientSecret` should look like this: `04f4ca22101529a3503d5a653a877b4e8403edf0`.
- `JWTSecret`: equal to `CasdoorConfig.JWTSECRET`, which is used to parse token to claims.  
- `OrganizationName`: equal to `CasdoorConfig.ORGANIZATIONNAME`, the organization's name.

You could refer to the below code fragment to start `CasdoorLoginActivity` when you need to:

```java
Intent intent = new Intent();
intent.setClass(activity, CasdoorLoginActivity.class);
// Before starting activity, you should init config
intent.putExtra(CasdoorConfig.ENDPOINT, "https://door.casbin.com");
intent.putExtra(CasdoorConfig.CLIENTID, "0ba528121ea87b3eb54d");
intent.putExtra(CasdoorConfig.CLIENTSECRET, "04f4ca22101529a3503d5a653a877b4e8403edf0");
intent.putExtra(CasdoorConfig.JWTSECRET, "04f4ca22101529a3503d5a653a877b4e8403edf004f4ca22101529a3503d5a653a877b4e8403edf004f4ca22101529a3503d5a653a877b4e8403edf0");
intent.putExtra(CasdoorConfig.ORGANIZATIONNAME, "casbin-oa");
startActivity(intent);
```

#### `String CasdoorUserToken.GetUserToken(FragmentActivity activity)`

After logging in, you could get the user token string through method `GetUserToken`, it should be noticed that this user token is `JSON Web Token (JWT)`, thus, it is available to get user's information without doing any extra request.

> JWT is a means of transmitting information between two parties in a compact, verifiable form.
>
> The bits of information encoded in the body of a JWT are called `claims`. The expanded form of the JWT is in a JSON format, so each `claim` is a key in the JSON object.
>
> JWTs can be cryptographically signed (making it a [JWS](https://tools.ietf.org/html/rfc7515)) or encrypted (making it a [JWE](https://tools.ietf.org/html/rfc7516)).
>
> This adds a powerful layer of verifiability to the user of JWTs. The receiver has a high degree of confidence that the JWT has not been tampered with by verifying the signature, for instance.
>
> The compact representation of a signed JWT is a string that has three parts, each separated by a `.`:
>
> ```
> eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.ipevRNuRP6HflG8cFKnmUPtypruRC4fb1DWtoLL62SY
> ```
>
> Each part is [Base64URL](https://en.wikipedia.org/wiki/Base64)-encoded. The first part is the header, which at a minimum needs to specify the algorithm used to sign the JWT. The second part is the body. This part has all the claims of this JWT encoded in it. The final part is the signature. It's computed by passing a combination of the header and body through the algorithm specified in the header.
>
> If you pass the first two parts through a base 64 url decoder, you'll get the following (formatting added for clarity):
>
> ```json
> header {
>   "alg": "HS256"
> }
> body {
>   "sub": "Joe"
> }
> ```
>
> In this case, the information we have is that the HMAC using SHA-256 algorithm was used to sign the JWT. And, the body has a single claim, `sub` with value `Joe`. In casdoor sdk, the claims struct is:
>
> ```java
> public class CasdoorClaims {
>     String Organization;
>     String UserName;
>     String Type;
> 
>     String Name;
>     String Avatar;
>     String Email;
>     String Phone;
> 
>     String Affiliation;
>     String Tag;
>     String Language;
>     int Score;
> 
>     boolean IsAdmin;
>     String Aud;
>     int Exp;
>     int Iat;
>     String Iss;
>     int Nbf;
> }	
> ```
>
> There are a number of standard claims, called [Registered Claims](https://tools.ietf.org/html/rfc7519#section-4.1), in the specification and `sub` (for subject) is one of them.
>
> To compute the signature, you need a secret key to sign it. Which is `CasdoorConfig.JWTSECRET` in our case.

#### `CasdoorClaims CasdoorUserToken.ParseJwtToken(FragmentActivity activity, String token)`

As mentioned above, in our case, the user's personal information is underlying of the JWT, to make it easier to use token get user info, casdoor android sdk provides this method to parse token to claim. 

> Using [JJWT](https://github.com/jwtk/jjwt) could easily do all operations of JSON Web Token for Java and Android, actually, this method is based on this, thus, it is 100% compatible to use JJWT instead of this method when you have some advanced customization operations of JWT. You could refer to the below code fragment when you want to customize your JWT parser:
>
> ```java
> Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(CasdoorConfig.GetConfig(activity).JWTSecret.getBytes())).build().parseClaimsJws(token).getBody();
> ```

After successfully obtaining the `CasdoorClaims`, you could use all kinds of `get` method to get instance variables of claims:

- `String getOrganization()`

- `String getUserName()`
- `String getType()`

- `String getName()`

- `String getAvatar()`

- `String getEmail()`

- `String getPhone()`

- `String getAffiliation()`

- `String getTag()`
- `String getLanguage()`

- `int getScore()`

- `boolean isAdmin()`

- `String getAud()`

- `int getExp()`

- `int getIat()`

- `String getIss()`
- `int getNbf()`

- `String getSub()`

To achieve get user's information effect when have logged in, you could refer to the below code fragment in `onCreateView` method of :

```java
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentUserInfoBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    String userToken = CasdoorUserToken.GetUserToken(getActivity());
    CasdoorClaims casdoorClaims = CasdoorUserToken.ParseJwtToken(getActivity(), userToken);

    TextView name = binding.UserInfoTableName;
    name.setText(casdoorClaims.getName());

    TextView email = binding.UserInfoTableEmail;
    email.setText(casdoorClaims.getEmail());

    TextView phone = binding.UserInfoTablePhone;
    phone.setText(casdoorClaims.getPhone());

    TextView organization = binding.UserInfoTableOrganization;
    organization.setText(casdoorClaims.getOrganization());
}
```



