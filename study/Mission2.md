[인프런 - Readable Code : 읽기 좋은 코드를 작성하는 사고법 강의 -박우빈](https://www.inflearn.com/course/readable-code-%EC%9D%BD%EA%B8%B0%EC%A2%8B%EC%9D%80%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1%EC%82%AC%EA%B3%A0%EB%B2%95/dashboard
)

### 미션 내용

1. 아래 코드와 설명을 보고, [섹션 3. 논리, 사고의 흐름]에서 이야기하는 내용을 중심으로 읽기 좋은 코드로 리팩토링해 봅시다.
2. SOLID에 대하여 자기만의 언어로 정리해 봅시다.

### 제출 기한

2025-03-07 금 23:59

### 비고

[자기만의 언어]

여러 블로그에 있는 내용, 다른 사람이 작성한 내용을 볼 수도 있지만, 결국 모든 학습의 종착지는 ‘자신의 언어로 표현할 수 있는가’ 입니다.

저도 강의를 준비하고 촬영하면서 저만의 언어로 이해하고 곱씹은 내용을 여러분께 설명하고 있는 중이니까요 🙂

<br />

# 미션과 강의를 보면서 느낀점.

작성된 코드에서 강사님이 하나하나 설명해주면서 리팩토링을 해주는 것을 보면서 따라갔다. 보면서 드는 생각은

- **_어떻게 저렇게 리팩토링을 할 수 있을까?_**
- **_강사님은 어떤 고민과 어떤 시간들을 보내셨길래 저렇게 할 수 있을까?_**
- **_나도 할 수 있을까?_**
- **_이게 더 읽기가 좋아진게 맞을까?_**

이런 생각들을 하면서 강의를 들었다. **_우선 나는 강사님처럼 바로 할 수는 없다_**. 하지만 강의를 보고 곱씹어보며 강사님의 생각을 따라가려고 노력해볼 수는 있다. 강의는 내가 생각할 방향에 대해서 알게되는
것이라고 생각한다. 강의를 보면서 얻게된 재료를 내가 다듬고 요리할 수 있도록 스스로 생각해보고 시도해보고 노력해보는 것이 중요한 것 같다. 비록 지금 스터디가 매우 빠른 페이스로 진행되고 있어서 바로 시도해보지는
못하는게 아쉽다. 현재 상황에서 할 수 있는 최선을 다하자.

<br />

# 미션

- 사용자가 생성한 ***‘주문’이 유효한지를 검증***하는 메소드.
- Order는 주문 객체이고, 필요하다면 Order에 추가적인 메소드를 만들어도 된다. (Order 내부의 구현을 구체적으로 할 필요는 없다.)
- 필요하다면 메소드를 추출할 수 있다.

```java
public boolean validateOrder(Order order) {
    if (order.getItems().size() == 0) {
        log.info("주문 항목이 없습니다.");
        return false;
    } else {
        if (order.getTotalPrice() > 0) {
            if (!order.hasCustomerInfo()) {
                log.info("사용자 정보가 없습니다.");
                return false;
            } else {
                return true;
            }
        } else if (!(order.getTotalPrice() > 0)) {
            log.info("올바르지 않은 총 가격입니다.");
            return false;
        }
    }
    return true;
}
```

분석

```java
// 주문 객체를 생성하는데 검증을 하는 로직이구나.
public boolean validateOrder(Order order) {
    if (order.getItems().size() == 0) {  // 파라미터로 받은 주문에 아이템이 아무것도 없는가?
        log.info("주문 항목이 없습니다.");
        return false;  // 주문항목이 없다. 결과를 boolean 으로 응답한다.
    } else {  // 파라미터로 받은 주문 객체에 아이템이 담겨있을 경우
        if (order.getTotalPrice() > 0) {  // 주문객체의 총 가격이 0보다 큰지 검사한다.
            if (!order.hasCustomerInfo()) {  // 주문 객체에 사용자 정보가 있는지 검사한다.
                log.info("사용자 정보가 없습니다.");
                return false;
            } else {  // 사용자의 정보를 담고 있다. + 총 주문 가격에 0 보다 크다.
                return true;
            }
        } else if (!(order.getTotalPrice() > 0)) {  // 주문 총 가격이 0보다 작은지 검사한다.
            log.info("올바르지 않은 총 가격입니다.");
            return false;
        }
    }
    return true;
}
```

이 메소드의 역할은 주문 객체가 적절한지를 검증하는 것이다.

<br />

### 생각정리

1. 왜 주문 객체를 생성할 때 검증하지 않았는가, 메소드 이름을 보고 생성자에서 검증을 하는 것이라고 먼저 생각이 들었다.
2. 주문 객체가 주문자의 정보를 검증해야하는가 (주문을 생성할 때 검증된 사용자의 정보를 파라미터로 받으면 될 것 같은데?)
3. 두 가지 방식으로 나눠볼 것이다.
    - 생성자에서 검증하는 메소드로 변경
    - 기존의 의도대로 코드를 리팩토링

<br />

## 1. 기존의 의도대로 코드를 리팩토링

### Early Return + space

중첩된 분기 처리로 가독성이 너무 좋지 않다. 배웠던 `Early Return` 으로 리팩토링

```java
public boolean validateOrder(Order order) {

    // 주문 객체의 상품이 0 또는 음수라면 false 리턴
    if (order.getItems().size() <= 0) {
        log.info("주문 항목이 없습니다");
        return false;
    }

    // 주문 총 가격이 0보다 작다면 false 리턴
    if (!(order.getTotalPrice() > 0)) {
        log.info("올바르지 않는 총 가격입니다.)";
        return false;
    }

    // 주문 객체에 사용자 정보가 없다면 false 리턴
    if (!order.hasCustomerInfo()) {
        log.info("사용자 정보가 없습니다.");
        return false;
    }

    return true;
} 
```

- 주문에 아무것도 아이템이 없는가에 대한 검증
- 주문의 총 가격이 0보다 작은지에 대한 검증
- 주문 객체에 사용자 정보가 존재하는지에 대한 검증

기본에 복잡한 분기문에서 검증하려는 로직은 모두 존재한다. `Early Return` 을 사용하여 복잡했던 구조가 한 눈에 잘 보이도록 코드가 리팩토링 되었다.

<br />

### OOP

객체의 데이터에 직접 접근해서 검사하는 부분을 리팩토링

```java
// 수정 전
if(order.getItems().size() <=0){
    log.info("주문 항목이 없습니다");
    return false;
}
// 수정 후
if(order.doesNotHaveItems()){
    log.info("주문 항목이 없습니다");
    return false;
}

class Order {
    //...
    public boolean doesNotHaveItems() {
        return items.size() <= 0;
    }
}
```

order 객체의 데이터를 직접 접근해서 검증하던 것에서 order 객체에게 공개된 메소드를 통해서 협력하도록 변경

<br />

### 부정 (!)

부정 ! 을 이용할 경우, 한 번 더 생각을 거쳐야한다. 이는 뇌의 리소스를 더 사용하게 하여 리팩토링한다

```java
    // 주문 총 가격이 0보다 작다면 false 리턴
if(!(order.getTotalPrice() >0)) {
    log.info("올바르지 않는 총 가격입니다.)";
  return false;
}
```

1. 부정연산자 제거

```java
if(order.getTotalPrice() <=0)){
    log.info("올바르지 않는 총 가격입니다.)";
    return false;
}
```

1. 객체 메소드로 물어보기

```java
if(order.isNegativeTotalPrice()){
    log.info("올바르지 않는 총 가격입니다.)";
    return false;
}

class Order {
	//...

    public boolean isNegativeTotalPrice() {
        return totalPrice <= 0;
    }
}
```

1. 부정 연산자 제거

```java
if(!order.hasCustomerInfo()){
    log.info("사용자 정보가 없습니다.");
    return false;
}
```

```java
if(order.hasNoCustomerInfo()){
    log.info("사용자 정보가 없습니다.");
    return false;
}
```

<br />

### 결과

```java
public boolean validateOrder(Order order) {

    if (order.doesNotHaveItems()) {
        log.info("주문 항목이 없습니다");
        return false;
    }

    if (order.isNegativeTotalPrice()) {
        log.info("올바르지 않는 총 가격입니다.)";
        return false;
    }

    if (order.hasNoCustomerInfo()) {
        log.info("사용자 정보가 없습니다.");
        return false;
    }

    return true;
} 
```

기존 방식을 유지한 채로 리팩토링을 한 결과이다. 기존의 복잡한 분기문보다 메소드의 목적과 가독성을 향상되었다고 생각한다.

그런데 주문 객체를 생성한 다음에 검증을 해야할까? 주문 객체를 생성할 때 생성자 내부에서 검증을 한다면 초기화 시에 완전한 주문 객체를 보장할 수 있을텐데. 그리고 외부에 공개하지 않고 내부적으로만 로직을 알아도
된다고 생각했다.

<br />

## Order 생성시에 검증도록 수정하기

```java
public class Order {

    private final Item[] items;
    private final Customer customer;
    private final long totalPrice;

    public Order(Customer customer, Item... items) {
        if (customer == null) {
            throw new AppException("사용자 정보가 없습니다.");
        }

        if (items.size == 0) {
            throw new AppException("주문 항목이 없습니다.");
        }

        int total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        if (total <= 0) {
            throw new AppException("올바르지 않은 총 가격입니다.");
        }

        // 초기화
    }
}
```

주문을 생성하면서 검증로직을 진행하도록 수정했다. 수정 해보니, 구현이 길어지면서 생성자가 길어지고 가독성이 떨어져 검증 로직을 추상적으로 하기 위해 메소드를 분리하는게 좋아보인다.

```java
private void validate(Customer customer, long totalPrice) {
    if (customer == null) {
        throw new AppException("사용자 정보가 없습니다.");
    }

    if (items.size == 0) {
        throw new AppException("주문 항목이 없습니다.");
    }

    if (totalPrice <= 0) {
        throw new AppException("올바르지 않은 총 가격입니다.");
    }
}

public Order(Customer customer, Item... items) {
    int totalPrice = 0;
    for (Item item : items) {
        totalPrice += item.getPrice();
    }

    validate(customer, totalPrice);

    this.customer = customer;
    this.totalPrice = totalPrice;
	...
}

```

<br />

# SOLID

SRP : Single Responsibility Principle

OCP : Open-Closed Principle

LSP : Liskov Substitution Priciple

ISP : Interface Segregation Principle

DIP : Dependency Inversion Priciple

# SRP : Single Responsibility Principle

단일 책임 원칙

- 하나의 클래스는 단 한 가지의 변경 이유만을 가져야 한다.
    - 변경 이유 = 책임
- 객체가 가진 공개 메소드, 필드, 상수 등은 해당 객체의 단일 책임에 의해서만 변경되는가?
- 관심사의 분리
- 높은 응집도, 낮은 결합도

책임이라는 경계를 발견해내는게 굉장히 어렵다. 우리는 우리가 설계한 객체가 단 하나의 책임을 지니고 있는가 끊임없이 고민하고 질문해야한다.

책임을 볼 줄 아는 눈을 기르자

# OCP : Open-Closed Principle

- 확장에는 열려 있고, 수정에는 닫혀 있어야 한다.
- → 기존 코드의 변경 없이, 시스템의 기능을 확장할 수 있어야한다.
- 추상화와 다형성을 활용해서 OCP를 지킬 수 있다.

## LSP : Liskov substitution Priciple

리스코프 치환 법칙

- 상속 구조에서, 부모 클래스의 인스턴스를 자식 클래스의 인스턴스로 치환할 수 있어야 한다.
- → 자식 클래스는 부모 클래스의 책임을 준수하며, 부모 클래스의 행동을 변경하지 않아야 한다.
- LSP를 위반하면, 상속 클래스를 사용할 대 오작동, 예상 밖의 예외가 발생하거나, 이를 방지하기 위한 불필요한 타입 체크가 동반될 수 있다.

## ISP : Interface Segregation Principle

- 클라이언트는 자신이 사용하지 않는 인터페이스에 의존하면 안된다.
- → 인터페이스를 잘게 쪼개라!
- ISP를 위반하면, 불필요한 의존성으로 인해 결합도가 높아지고, 특정 기능의 변경이 여러 클래스에 영향을 미칠 수 있다.

## DIP : Dependecy Inversion Principle

의존성 역전 원칙

- 상위 수준의 모듈은 하위 수준의 모듈에 의존해서는 안된다.
- → 둘 모두 추상화에 의존해야 한다.
- 의존성의 순방향 : 고수준 모듈이 저수준 모듈을 참조하는 것
- 의존성 역방향 : 고수준, 저수준 모듈이 모두 추상화에 의존하는 것
- → 저수준 모듈이 변경되더라도, 고수준 모듈에는 영향이 가지 않는다.