package org.timothy.shard.domain;

public class User {
    private Long id;

    private String name;

    private Integer sex;

    private String remark;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;

        private String name;

        private Integer sex;

        private String remark;

        public Builder() {
        }

        public Builder(Long id, String name, Integer sex, String remark) {
            this.id = id;
            this.name = name;
            this.sex = sex;
            this.remark = remark;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder sex(Integer sex) {
            this.sex = sex;
            return this;
        }

        public Builder remark(String remark) {
            this.remark = remark;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.sex = builder.sex;
        this.remark = builder.remark;
    }

}