# This Python file uses the following encoding: utf-8
"""autogenerated by genpy from morai_msgs/FaultInjection_Controller.msg. Do not edit."""
import codecs
import sys
python3 = True if sys.hexversion > 0x03000000 else False
import genpy
import struct


class FaultInjection_Controller(genpy.Message):
  _md5sum = "29fcfb33853ca6e2114fbfdf30c06eaf"
  _type = "morai_msgs/FaultInjection_Controller"
  _has_header = False  # flag to mark the presence of a Header object
  _full_text = """int32 unique_id

int32 fault_location
int32 fault_class
int32 fault_subclass
"""
  __slots__ = ['unique_id','fault_location','fault_class','fault_subclass']
  _slot_types = ['int32','int32','int32','int32']

  def __init__(self, *args, **kwds):
    """
    Constructor. Any message fields that are implicitly/explicitly
    set to None will be assigned a default value. The recommend
    use is keyword arguments as this is more robust to future message
    changes.  You cannot mix in-order arguments and keyword arguments.

    The available fields are:
       unique_id,fault_location,fault_class,fault_subclass

    :param args: complete set of field values, in .msg order
    :param kwds: use keyword arguments corresponding to message field names
    to set specific fields.
    """
    if args or kwds:
      super(FaultInjection_Controller, self).__init__(*args, **kwds)
      # message fields cannot be None, assign default values for those that are
      if self.unique_id is None:
        self.unique_id = 0
      if self.fault_location is None:
        self.fault_location = 0
      if self.fault_class is None:
        self.fault_class = 0
      if self.fault_subclass is None:
        self.fault_subclass = 0
    else:
      self.unique_id = 0
      self.fault_location = 0
      self.fault_class = 0
      self.fault_subclass = 0

  def _get_types(self):
    """
    internal API method
    """
    return self._slot_types

  def serialize(self, buff):
    """
    serialize message into buffer
    :param buff: buffer, ``StringIO``
    """
    try:
      _x = self
      buff.write(_get_struct_4i().pack(_x.unique_id, _x.fault_location, _x.fault_class, _x.fault_subclass))
    except struct.error as se: self._check_types(struct.error("%s: '%s' when writing '%s'" % (type(se), str(se), str(locals().get('_x', self)))))
    except TypeError as te: self._check_types(ValueError("%s: '%s' when writing '%s'" % (type(te), str(te), str(locals().get('_x', self)))))

  def deserialize(self, str):
    """
    unpack serialized message in str into this message instance
    :param str: byte array of serialized message, ``str``
    """
    if python3:
      codecs.lookup_error("rosmsg").msg_type = self._type
    try:
      end = 0
      _x = self
      start = end
      end += 16
      (_x.unique_id, _x.fault_location, _x.fault_class, _x.fault_subclass,) = _get_struct_4i().unpack(str[start:end])
      return self
    except struct.error as e:
      raise genpy.DeserializationError(e)  # most likely buffer underfill


  def serialize_numpy(self, buff, numpy):
    """
    serialize message with numpy array types into buffer
    :param buff: buffer, ``StringIO``
    :param numpy: numpy python module
    """
    try:
      _x = self
      buff.write(_get_struct_4i().pack(_x.unique_id, _x.fault_location, _x.fault_class, _x.fault_subclass))
    except struct.error as se: self._check_types(struct.error("%s: '%s' when writing '%s'" % (type(se), str(se), str(locals().get('_x', self)))))
    except TypeError as te: self._check_types(ValueError("%s: '%s' when writing '%s'" % (type(te), str(te), str(locals().get('_x', self)))))

  def deserialize_numpy(self, str, numpy):
    """
    unpack serialized message in str into this message instance using numpy for array types
    :param str: byte array of serialized message, ``str``
    :param numpy: numpy python module
    """
    if python3:
      codecs.lookup_error("rosmsg").msg_type = self._type
    try:
      end = 0
      _x = self
      start = end
      end += 16
      (_x.unique_id, _x.fault_location, _x.fault_class, _x.fault_subclass,) = _get_struct_4i().unpack(str[start:end])
      return self
    except struct.error as e:
      raise genpy.DeserializationError(e)  # most likely buffer underfill

_struct_I = genpy.struct_I
def _get_struct_I():
    global _struct_I
    return _struct_I
_struct_4i = None
def _get_struct_4i():
    global _struct_4i
    if _struct_4i is None:
        _struct_4i = struct.Struct("<4i")
    return _struct_4i
