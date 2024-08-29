// Generated by gencpp from file morai_msgs/FaultInjection_Sensor.msg
// DO NOT EDIT!


#ifndef MORAI_MSGS_MESSAGE_FAULTINJECTION_SENSOR_H
#define MORAI_MSGS_MESSAGE_FAULTINJECTION_SENSOR_H


#include <string>
#include <vector>
#include <memory>

#include <ros/types.h>
#include <ros/serialization.h>
#include <ros/builtin_message_traits.h>
#include <ros/message_operations.h>

#include <geometry_msgs/Vector3.h>
#include <geometry_msgs/Vector3.h>

namespace morai_msgs
{
template <class ContainerAllocator>
struct FaultInjection_Sensor_
{
  typedef FaultInjection_Sensor_<ContainerAllocator> Type;

  FaultInjection_Sensor_()
    : unique_id(0)
    , sensor_id(0)
    , fault_class(0)
    , fault_subclass(0)
    , local_position_offset()
    , local_rotation_offset()  {
    }
  FaultInjection_Sensor_(const ContainerAllocator& _alloc)
    : unique_id(0)
    , sensor_id(0)
    , fault_class(0)
    , fault_subclass(0)
    , local_position_offset(_alloc)
    , local_rotation_offset(_alloc)  {
  (void)_alloc;
    }



   typedef int32_t _unique_id_type;
  _unique_id_type unique_id;

   typedef int32_t _sensor_id_type;
  _sensor_id_type sensor_id;

   typedef int32_t _fault_class_type;
  _fault_class_type fault_class;

   typedef int32_t _fault_subclass_type;
  _fault_subclass_type fault_subclass;

   typedef  ::geometry_msgs::Vector3_<ContainerAllocator>  _local_position_offset_type;
  _local_position_offset_type local_position_offset;

   typedef  ::geometry_msgs::Vector3_<ContainerAllocator>  _local_rotation_offset_type;
  _local_rotation_offset_type local_rotation_offset;





  typedef boost::shared_ptr< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> > Ptr;
  typedef boost::shared_ptr< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> const> ConstPtr;

}; // struct FaultInjection_Sensor_

typedef ::morai_msgs::FaultInjection_Sensor_<std::allocator<void> > FaultInjection_Sensor;

typedef boost::shared_ptr< ::morai_msgs::FaultInjection_Sensor > FaultInjection_SensorPtr;
typedef boost::shared_ptr< ::morai_msgs::FaultInjection_Sensor const> FaultInjection_SensorConstPtr;

// constants requiring out of line definition



template<typename ContainerAllocator>
std::ostream& operator<<(std::ostream& s, const ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> & v)
{
ros::message_operations::Printer< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> >::stream(s, "", v);
return s;
}


template<typename ContainerAllocator1, typename ContainerAllocator2>
bool operator==(const ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator1> & lhs, const ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator2> & rhs)
{
  return lhs.unique_id == rhs.unique_id &&
    lhs.sensor_id == rhs.sensor_id &&
    lhs.fault_class == rhs.fault_class &&
    lhs.fault_subclass == rhs.fault_subclass &&
    lhs.local_position_offset == rhs.local_position_offset &&
    lhs.local_rotation_offset == rhs.local_rotation_offset;
}

template<typename ContainerAllocator1, typename ContainerAllocator2>
bool operator!=(const ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator1> & lhs, const ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator2> & rhs)
{
  return !(lhs == rhs);
}


} // namespace morai_msgs

namespace ros
{
namespace message_traits
{





template <class ContainerAllocator>
struct IsMessage< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> >
  : TrueType
  { };

template <class ContainerAllocator>
struct IsMessage< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> const>
  : TrueType
  { };

template <class ContainerAllocator>
struct IsFixedSize< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> >
  : TrueType
  { };

template <class ContainerAllocator>
struct IsFixedSize< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> const>
  : TrueType
  { };

template <class ContainerAllocator>
struct HasHeader< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> >
  : FalseType
  { };

template <class ContainerAllocator>
struct HasHeader< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> const>
  : FalseType
  { };


template<class ContainerAllocator>
struct MD5Sum< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> >
{
  static const char* value()
  {
    return "1d09067e718a7214f8177394317316b2";
  }

  static const char* value(const ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator>&) { return value(); }
  static const uint64_t static_value1 = 0x1d09067e718a7214ULL;
  static const uint64_t static_value2 = 0xf8177394317316b2ULL;
};

template<class ContainerAllocator>
struct DataType< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> >
{
  static const char* value()
  {
    return "morai_msgs/FaultInjection_Sensor";
  }

  static const char* value(const ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator>&) { return value(); }
};

template<class ContainerAllocator>
struct Definition< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> >
{
  static const char* value()
  {
    return "int32 unique_id\n"
"int32 sensor_id\n"
"\n"
"int32 fault_class\n"
"int32 fault_subclass\n"
"\n"
"geometry_msgs/Vector3 local_position_offset\n"
"geometry_msgs/Vector3 local_rotation_offset\n"
"\n"
"================================================================================\n"
"MSG: geometry_msgs/Vector3\n"
"# This represents a vector in free space. \n"
"# It is only meant to represent a direction. Therefore, it does not\n"
"# make sense to apply a translation to it (e.g., when applying a \n"
"# generic rigid transformation to a Vector3, tf2 will only apply the\n"
"# rotation). If you want your data to be translatable too, use the\n"
"# geometry_msgs/Point message instead.\n"
"\n"
"float64 x\n"
"float64 y\n"
"float64 z\n"
;
  }

  static const char* value(const ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator>&) { return value(); }
};

} // namespace message_traits
} // namespace ros

namespace ros
{
namespace serialization
{

  template<class ContainerAllocator> struct Serializer< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> >
  {
    template<typename Stream, typename T> inline static void allInOne(Stream& stream, T m)
    {
      stream.next(m.unique_id);
      stream.next(m.sensor_id);
      stream.next(m.fault_class);
      stream.next(m.fault_subclass);
      stream.next(m.local_position_offset);
      stream.next(m.local_rotation_offset);
    }

    ROS_DECLARE_ALLINONE_SERIALIZER
  }; // struct FaultInjection_Sensor_

} // namespace serialization
} // namespace ros

namespace ros
{
namespace message_operations
{

template<class ContainerAllocator>
struct Printer< ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator> >
{
  template<typename Stream> static void stream(Stream& s, const std::string& indent, const ::morai_msgs::FaultInjection_Sensor_<ContainerAllocator>& v)
  {
    s << indent << "unique_id: ";
    Printer<int32_t>::stream(s, indent + "  ", v.unique_id);
    s << indent << "sensor_id: ";
    Printer<int32_t>::stream(s, indent + "  ", v.sensor_id);
    s << indent << "fault_class: ";
    Printer<int32_t>::stream(s, indent + "  ", v.fault_class);
    s << indent << "fault_subclass: ";
    Printer<int32_t>::stream(s, indent + "  ", v.fault_subclass);
    s << indent << "local_position_offset: ";
    s << std::endl;
    Printer< ::geometry_msgs::Vector3_<ContainerAllocator> >::stream(s, indent + "  ", v.local_position_offset);
    s << indent << "local_rotation_offset: ";
    s << std::endl;
    Printer< ::geometry_msgs::Vector3_<ContainerAllocator> >::stream(s, indent + "  ", v.local_rotation_offset);
  }
};

} // namespace message_operations
} // namespace ros

#endif // MORAI_MSGS_MESSAGE_FAULTINJECTION_SENSOR_H
